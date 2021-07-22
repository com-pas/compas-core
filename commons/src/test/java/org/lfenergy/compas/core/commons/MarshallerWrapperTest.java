// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.XmlElementAPojo;
import org.lfenergy.compas.core.commons.model.XmlElementBPojo;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.*;

class MarshallerWrapperTest {
    @Test
    void marshaller_WhenMarshallAndUnmarshall_ThenShouldReturnOK() {
        var marshallerWrapper = new XmlElementPojoMarshallerWrapper.Builder().build();
        var pojo = createPOJO();

        // First marshal the Class with Private to a XML String.
        var xml = marshallerWrapper.marshall(pojo);
        assertNotNull(xml);

        // Next convert the XML String back again to a SCL Class.
        pojo = marshallerWrapper.unmarshall(xml.getBytes());
        assertNotNull(pojo);
    }

    @Test
    void unmarshaller_WhenUnmarshallWithInvalidXML_ThenExceptionIsThrown() {
        var marshallerWrapper = new XmlElementPojoMarshallerWrapper.Builder().build();
        var xml = "<invalid></invalid>".getBytes();

        // Next convert the XML String back again to a SCL Class.
        var exception = assertThrows(CompasException.class, () -> marshallerWrapper.unmarshall(xml));
        assertEquals(UNMARSHAL_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void marshaller_WhenInitializedWithEmptyConfig_ThenExceptionIsThrown() {
        var marshallerWrapperBuilder =
                new XmlElementPojoMarshallerWrapper.Builder().withProperties("invalid-configs/marshaller-empty-config.yml");

        // Expected that creation to fail, because there are no schemas configured.
        var exception = assertThrows(CompasException.class, () -> marshallerWrapperBuilder.build());
        assertEquals(CONFIGURATION_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void marshaller_WhenInitializedWithInvalidConfig_ThenExceptionIsThrown() {
        var marshallerWrapperBuilder =
                new XmlElementPojoMarshallerWrapper.Builder().withProperties("invalid-configs/marshaller-invalid-config.yml");

        // Expected that creation to fail, because this configuration is invalid.
        var exception = assertThrows(CompasException.class, () -> marshallerWrapperBuilder.build());
        assertEquals(PROPERTY_ERROR_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void marshaller_WhenInitializedWithInvalidContextPath_ThenExceptionIsThrown() {
        var marshallerWrapperBuilder =
                new XmlElementPojoMarshallerWrapper.Builder().withProperties("invalid-configs/marshaller-invalid-contextpath-config.yml");

        // Expected that creation to fail, because this configuration is invalid.
        var exception = assertThrows(CompasException.class, () -> marshallerWrapperBuilder.build());
        assertEquals(CREATION_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void marshaller_WhenInitializedWithInvalidXsdPath_ThenExceptionIsThrown() {
        var marshallerWrapperBuilder =
                new XmlElementPojoMarshallerWrapper.Builder().withProperties("invalid-configs/marshaller-invalid-xsdpath-config.yml");

        // Expected that creation to fail, because this configuration is invalid.
        var exception = assertThrows(CompasException.class, () -> marshallerWrapperBuilder.build());
        assertEquals(RESOURCE_NOT_FOUND_ERROR_CODE, exception.getErrorCode());
    }

    private XmlElementAPojo createPOJO() {
        var aPojo = new XmlElementAPojo();
        aPojo.setId(UUID.randomUUID().toString());

        var bPojo = new XmlElementBPojo();
        bPojo.setId(UUID.randomUUID().toString());
        bPojo.setName("SOMENAME");
        aPojo.setOther(bPojo);

        return aPojo;
    }

    private static class XmlElementPojoMarshallerWrapper extends MarshallerWrapper<XmlElementAPojo> {
        public XmlElementPojoMarshallerWrapper(Unmarshaller jaxbUnmarshaller, Marshaller jaxbMarshaller) {
            super(jaxbUnmarshaller, jaxbMarshaller);
        }

        @Override
        protected Class<XmlElementAPojo> getResultClass() {
            return XmlElementAPojo.class;
        }

        private static class Builder extends MarshallerWrapper.Builder<XmlElementPojoMarshallerWrapper, XmlElementAPojo> {
            public Builder() {
                withProperties("xml-element-marshaller-config.yml");
            }

            @Override
            protected XmlElementPojoMarshallerWrapper createMarshallerWrapper(Unmarshaller jaxbUnmarshaller, Marshaller jaxbMarshaller) {
                return new XmlElementPojoMarshallerWrapper(jaxbUnmarshaller, jaxbMarshaller);
            }
        }
    }
}