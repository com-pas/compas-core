// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2003.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl2003.model.SCL;
import org.lfenergy.compas.scl2003.model.THeader;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MarshallerWrapperTest {
    @Test
    void testShouldReturnOKWhenMarshallAndUnmarshall() throws Exception {
        MarshallerWrapper marshallerWrapper = createWrapper("classpath:application.yml");
        SCL scl = createSCD(UUID.randomUUID(), "1.0", "1.0");
        String xml = marshallerWrapper.marshall(scl);

        scl = marshallerWrapper.unmarshall(xml.getBytes());
        assertNotNull(scl);
    }

    @Test
    void testShouldReturnOKWhenMarshallAndUnmarshallWithMapInitialization() throws Exception {
        MarshallerWrapper marshallerWrapper = createWrapper(Collections.singletonMap(SclConstants.XML_DEFAULT_NS_PREFIX, SclConstants.XML_DEFAULT_XSD_PATH));
        SCL scl = createSCD(UUID.randomUUID(), "1.0", "1.0");
        String xml = marshallerWrapper.marshall(scl);

        scl = marshallerWrapper.unmarshall(xml.getBytes());
        assertNotNull(scl);
    }

    private MarshallerWrapper createWrapper(String path) throws Exception {
        return (new MarshallerWrapper.Builder()).withProperties(path).build();
    }

    private MarshallerWrapper createWrapper(Map<String, String> schemaMap) throws Exception {
        return (new MarshallerWrapper.Builder()).withSchemaMap(schemaMap).build();
    }

    private SCL createSCD(UUID uuid, String hRevision, String hVersion) {
        SCL scl = new SCL();
        THeader tHeader = new THeader();
        tHeader.setRevision(hRevision);
        tHeader.setVersion(hVersion);
        tHeader.setId(uuid.toString());
        scl.setHeader(tHeader);

        return scl;
    }
}