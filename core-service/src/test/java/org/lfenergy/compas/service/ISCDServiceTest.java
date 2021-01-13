// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.service;

import org.lfenergy.compas.commons.MarshallerWrapper;
import org.lfenergy.compas.exception.ScdException;
import org.lfenergy.compas.scl.SCL;
import org.lfenergy.compas.scl.THeader;
import org.lfenergy.compas.scl.THitem;
import org.lfenergy.compas.testhelpers.DefaultSCDSQLRepository;
import org.lfenergy.compas.testhelpers.SimpleSCD;
import org.lfenergy.compas.testhelpers.SimpleSCDService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class ISCDServiceTest {

    private final String FILE_NAME = "file.scd";
    private final String H_VERSION = "1.0";
    private final String H_REVISION = "1.0";
    private final UUID ID = UUID.randomUUID();

    @InjectMocks
    @Spy
    private SimpleSCDService simpleSCDService;

    @MockBean
    private DefaultSCDSQLRepository defaultSCDSQLRepository;

    @MockBean
    private MarshallerWrapper marshallerWrapper;

    @Autowired
    private ResourceLoader resourceLoader;

    private Jaxb2Marshaller jaxb2Marshaller;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks( this );
        jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(SCL.class);
        Resource resource = resourceLoader.getResource("classpath:schema/SCL.xsd");
        jaxb2Marshaller.setSchema(resource);
    }

    @Test
    public void shouldReturnOKWhenInitiateSCD(){

        SimpleSCD expected = new SimpleSCD();
        expected.setFileName(FILE_NAME);
        expected.setId(ID);
        expected.setRawXml(createSCD(ID, H_REVISION, H_VERSION));

        Mockito.doReturn(ID).when(simpleSCDService).getNextID(any(SimpleSCD.class));
        Mockito.when(defaultSCDSQLRepository.save(any(SimpleSCD.class))).thenReturn(expected);
        Mockito.when(marshallerWrapper.marshall(any(SCL.class))).thenReturn(new String(expected.getRawXml()));

        SimpleSCD result = simpleSCDService.initiateSCD(FILE_NAME, H_VERSION, H_REVISION);

        SCL scd = unmarshall(result.getRawXml());

        assertEquals(ID,result.getId());
        assertEquals(FILE_NAME,result.getFileName());
        assertEquals(H_REVISION,scd.getHeader().getRevision());
        assertEquals(H_VERSION,scd.getHeader().getVersion());
    }

    @Test
    public void shouldReturnOKWhenAddHistoryItem() throws ScdException {
        SimpleSCD storedSimpleSCD = new SimpleSCD();
        storedSimpleSCD.setFileName(FILE_NAME);
        storedSimpleSCD.setId(ID);
        storedSimpleSCD.setRawXml(createSCD(ID, H_REVISION, H_VERSION));

        SCL storedScd = unmarshall(createSCD(ID, H_REVISION, H_VERSION));

        SimpleSCD expected = new SimpleSCD();
        expected.setFileName(FILE_NAME);
        expected.setId(ID);

        SCL expectedScd = unmarshall(createSCD(ID, H_REVISION, H_VERSION));

        THeader.History history = new THeader.History();
        THitem tHitem = new THitem();
        tHitem.setWhat("what");
        tHitem.setWho("who");
        tHitem.setWhy("why");
        history.getHitem().add(tHitem);
        expectedScd.getHeader().setHistory(history);
        expected.setRawXml(marshall(expectedScd).getBytes());

        Mockito.when(defaultSCDSQLRepository.findById(ID)).thenReturn(Optional.of(storedSimpleSCD));
        Mockito.when(defaultSCDSQLRepository.save(any(SimpleSCD.class))).thenReturn(expected);
        Mockito.when(marshallerWrapper.unmarshall(storedSimpleSCD.getRawXml())).thenReturn(storedScd);
        Mockito.when(marshallerWrapper.marshall(any(SCL.class))).thenReturn(new String(expected.getRawXml()));


        SimpleSCD result = simpleSCDService.addHistoryItem(ID,"who","what", "why");
        assertNotNull(result);
        SCL resultScd = unmarshall(result.getRawXml());

        assertAll(
                () -> assertNotNull(resultScd),
                () -> assertNotNull(resultScd.getHeader()),
                () -> assertNotNull(resultScd.getHeader().getHistory())
        );
        List<THitem> hItems = resultScd.getHeader().getHistory().getHitem();

        assertTrue(hItems.size() == 1);
        THitem hItem = hItems.get(0);
        assertAll(
                () -> assertNotNull(hItem),
                () -> assertEquals("who",hItem.getWho()),
                () -> assertEquals("what",hItem.getWhat()),
                () -> assertEquals("why",hItem.getWhy())
        );
    }

    private SCL unmarshall(final byte[] xml) {
        ByteArrayInputStream input = new ByteArrayInputStream( xml);
        return (SCL) jaxb2Marshaller.unmarshal(new StreamSource(input));
    }

    public String marshall(final SCL obj){
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        jaxb2Marshaller.marshal(obj, result);

        return sw.toString();
    }

    private byte[] createSCD(UUID uuid, String hRevision, String hVersion){
        SCL scd = new SCL();
        scd.setVersion("2007");
        scd.setRevision("B");
        scd.setRelease((short) 4);
        THeader tHeader = new THeader();
        tHeader.setRevision(hRevision);
        tHeader.setVersion(hVersion);
        tHeader.setId(uuid.toString());
        scd.setHeader(tHeader);

        return marshall(scd).getBytes(StandardCharsets.UTF_8);

    }
}