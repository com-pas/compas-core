// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2003.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.scl.extensions.commons.CompasExtensionsConstants;
import org.lfenergy.compas.scl.extensions.model.ObjectFactory;
import org.lfenergy.compas.scl2003.model.SCL;
import org.lfenergy.compas.scl2003.model.THeader;
import org.lfenergy.compas.scl2003.model.TPrivate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.UNMARSHAL_ERROR_CODE;

class Scl2003MarshallerWrapperTest {
    @Test
    void marshaller_WhenMarshallAndUnmarshall_ThenShouldReturnOK() {
        var marshallerWrapper = createMarshallerWrapper();
        var scl = createSCD(UUID.randomUUID());

        // First marshal the Class with Private to a XML String.
        var xml = marshallerWrapper.marshall(scl);
        assertNotNull(xml);

        // Next convert the XML String back again to a SCL Class.
        scl = marshallerWrapper.unmarshall(xml.getBytes());
        assertNotNull(scl);
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

    private Scl2003MarshallerWrapper createMarshallerWrapper() {
        return new Scl2003MarshallerWrapper.Builder().build();
    }

    private SCL createSCD(UUID uuid) {
        SCL scl = new SCL();
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