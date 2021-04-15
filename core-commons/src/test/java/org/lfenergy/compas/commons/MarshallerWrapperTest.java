package org.lfenergy.compas.commons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lfenergy.compas.scl.SCL;
import org.lfenergy.compas.scl.THeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MarshallerWrapperTest {



    @Autowired
    private MarshallerWrapper marshallerWrapper;

    @Test
    void testShouldReturnOKWhenMarshallAndUnmarshall() {

        SCL scl = createSCD(UUID.randomUUID(),"1.0","1.0");
        String xml = marshallerWrapper.marshall(scl);

        scl = marshallerWrapper.unmarshall(xml.getBytes());
        assertEquals("2007",scl.getVersion());
        assertEquals("B",scl.getRevision());
        assertEquals(4,scl.getRelease());
    }

    @Test
    void testShouldReturnNOKWhenMarshallCauseWrongSCLVersion() {
        SCL scl = createSCD(UUID.randomUUID(),"1.0","1.0");
        scl.setVersion("2000");

        assertThrows(Exception.class, () -> marshallerWrapper.marshall(scl));
    }

    @Test
    void testShouldReturnNOKWhenUnmarshallCauseWrongSCLVersion() {
        final String PAYLOAD =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<SCL version=\"2000\" revision=\"B\" release=\"4\" xmlns=\"http://www.iec.ch/61850/2003/SCL\">\n" +
            "    <Header id=\"c44577af-1827-4e8c-9240-735a0ec47738\" version=\"1.0\" revision=\"1.0\"/>\n" +
            "</SCL>";

        assertThrows(Exception.class, () -> marshallerWrapper.unmarshall(PAYLOAD.getBytes()));
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