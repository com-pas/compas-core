// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2007b4.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.scl.extensions.commons.CompasExtensionsConstants;
import org.lfenergy.compas.scl.extensions.model.ObjectFactory;
import org.lfenergy.compas.scl2007b4.model.SCL;
import org.lfenergy.compas.scl2007b4.model.THeader;
import org.lfenergy.compas.scl2007b4.model.TPrivate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.MARSHAL_ERROR_CODE;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.UNMARSHAL_ERROR_CODE;

class Scl2007bMarshallerWrapperTest {
    @Test
    void marshallerWrapper_WhenMarshallAndUnmarshall_ThenShouldReturnOK() {
        var marshallerWrapper = createMarshallerWrapper();
        var scl = createSCD(UUID.randomUUID());

        // First marshal the Class with Private to a XML String.
        var xml = marshallerWrapper.marshall(scl);
        assertNotNull(xml);

        // Next convert the XML String back again to a SCL Class.
        scl = marshallerWrapper.unmarshall(xml.getBytes());
        assertNotNull(scl);
        assertEquals("2007", scl.getVersion());
        assertEquals("B", scl.getRevision());
        assertEquals(4, scl.getRelease());
    }

    @Test
    void marshall_WhenSCLContainsWrongVersionRevision_ThenShouldThrowException() {
        var marshallerWrapper = createMarshallerWrapper();
        SCL scl = createSCD(UUID.randomUUID());
        scl.setVersion("2000");
        scl.setRevision("A");
        scl.setRelease((short) 3);

        var exception = assertThrows(CompasException.class,
                () -> marshallerWrapper.marshall(scl));
        assertEquals(MARSHAL_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void unmarshaller_WhenUnmarshallWithInvalidXML_ThenExceptionIsThrown() {
        var marshallerWrapper = createMarshallerWrapper();
        var xml = "<invalid></invalid>".getBytes();

        // Next convert the XML String back again to a SCL Class.
        var exception = assertThrows(CompasException.class,
                () -> marshallerWrapper.unmarshall(xml));
        assertEquals(UNMARSHAL_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void unmarshall_WhenXMLContainsWrongVersionRevision_ThenShouldThrowException() {
        var payload =
                ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<SCL version=\"2000\" revision=\"A\" release=\"2\" xmlns=\"http://www.iec.ch/61850/2003/SCL\">\n" +
                        "    <Header id=\"c44577af-1827-4e8c-9240-735a0ec47738\" version=\"1.0\" revision=\"1.0\"/>\n" +
                        "</SCL>").getBytes();

        var marshallerWrapper = createMarshallerWrapper();
        var exception = assertThrows(CompasException.class,
                () -> marshallerWrapper.unmarshall(payload));
        assertEquals(UNMARSHAL_ERROR_CODE, exception.getErrorCode());
    }

    private Scl2007b4MarshallerWrapper createMarshallerWrapper() {
        return new Scl2007b4MarshallerWrapper.Builder().build();
    }

    private SCL createSCD(UUID uuid) {
        SCL scl = new SCL();
        scl.setVersion("2007");
        scl.setRevision("B");
        scl.setRelease((short) 4);
        THeader tHeader = new THeader();
        tHeader.setRevision("1.0");
        tHeader.setVersion("1.0");
        tHeader.setId(uuid.toString());
        scl.setHeader(tHeader);

        TPrivate tPrivate = new TPrivate();
        tPrivate.setType(CompasExtensionsConstants.COMPAS_SCL_EXTENSION_TYPE);
        tPrivate.getContent().add(new ObjectFactory().createSclName("SclName"));
        scl.getPrivate().add(tPrivate);

        return scl;
    }
}