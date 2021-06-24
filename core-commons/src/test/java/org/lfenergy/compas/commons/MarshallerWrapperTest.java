// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl.SCL;
import org.lfenergy.compas.scl.THeader;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MarshallerWrapperTest {
    @Test
    void testShouldReturnOKWhenMarshallAndUnmarshall() throws Exception {
        MarshallerWrapper marshallerWrapper = createWrapper("classpath:application.yml");
        SCL scl = createSCD(UUID.randomUUID(), "1.0", "1.0");
        String xml = marshallerWrapper.marshall(scl);

        scl = marshallerWrapper.unmarshall(xml.getBytes());
        assertEquals("2007", scl.getVersion());
        assertEquals("B", scl.getRevision());
        assertEquals(4, scl.getRelease());
    }

    @Test
    void testShouldReturnOKWhenMarshallAndUnmarshallWithMapInitialization() throws Exception {

        MarshallerWrapper marshallerWrapper = createWrapper(Collections.singletonMap("scl", "classpath:schema/SCL.xsd"));
        SCL scl = createSCD(UUID.randomUUID(), "1.0", "1.0");
        String xml = marshallerWrapper.marshall(scl);

        scl = marshallerWrapper.unmarshall(xml.getBytes());
        assertEquals("2007", scl.getVersion());
        assertEquals("B", scl.getRevision());
        assertEquals(4, scl.getRelease());
    }

    @Test
    void testShouldReturnNOKWhenMarshallCauseWrongSCLVersion() throws Exception {
        MarshallerWrapper marshallerWrapper = createWrapper("classpath:application.properties");
        SCL scl = createSCD(UUID.randomUUID(), "1.0", "1.0");
        scl.setVersion("2000");

        assertThrows(Exception.class, () -> marshallerWrapper.marshall(scl));
    }

    @Test
    void testShouldReturnNOKWhenUnmarshallCauseWrongSCLVersion() throws Exception {
        final String PAYLOAD =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<SCL version=\"2000\" revision=\"B\" release=\"4\" xmlns=\"http://www.iec.ch/61850/2003/SCL\">\n" +
                        "    <Header id=\"c44577af-1827-4e8c-9240-735a0ec47738\" version=\"1.0\" revision=\"1.0\"/>\n" +
                        "</SCL>";

        MarshallerWrapper marshallerWrapper = createWrapper("");
        assertThrows(Exception.class, () -> marshallerWrapper.unmarshall(PAYLOAD.getBytes()));
    }

    private MarshallerWrapper createWrapper(String path) throws Exception {
        return (new MarshallerWrapper.Builder()).withProperties(path).build();
    }

    private MarshallerWrapper createWrapper(Map<String,String> schemaMap) throws Exception {
        return (new MarshallerWrapper.Builder()).withSchemaMap(schemaMap).build();
    }

    private SCL createSCD(UUID uuid, String hRevision, String hVersion){
        SCL scl = new SCL();
        scl.setVersion("2007");
        scl.setRevision("B");
        scl.setRelease((short) 4);
        THeader tHeader = new THeader();
        tHeader.setRevision(hRevision);
        tHeader.setVersion(hVersion);
        tHeader.setId(uuid.toString());
        scl.setHeader(tHeader);

        return scl;
    }
}