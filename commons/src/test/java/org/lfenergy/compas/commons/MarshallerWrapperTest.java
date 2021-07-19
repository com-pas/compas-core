// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.commons.exception.CompasException;
import org.lfenergy.compas.commons.model.XmlElementAPojo;
import org.lfenergy.compas.commons.model.XmlElementBPojo;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.commons.exception.CompasErrorCode.*;

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
        var xml = "<invalid></invalid>";

        // Next convert the XML String back again to a SCL Class.
        var exception = assertThrows(CompasException.class,
                () -> new XmlElementPojoMarshallerWrapper.Builder().build().unmarshall(xml.getBytes()));
        assertEquals(UNMARSHAL_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void marshaller_WhenInitializedWithEmptyConfig_ThenExceptionIsThrown() {
        // Expected that creation to fail, because there are no schemas configured.
        var exception = assertThrows(CompasException.class,
                () -> new XmlElementPojoMarshallerWrapper.Builder().withProperties("invalid-configs/marshaller-empty-config.yml").build());
        assertEquals(CONFIGURATION_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void marshaller_WhenInitializedWithInvalidConfig_ThenExceptionIsThrown() {
        // Expected that creation to fail, because this configuration is invalid.
        var exception = assertThrows(CompasException.class,
                () -> new XmlElementPojoMarshallerWrapper.Builder().withProperties("invalid-configs/marshaller-invalid-config.yml").build());
        assertEquals(PROPERTY_ERROR_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void marshaller_WhenInitializedWithInvalidContextPath_ThenExceptionIsThrown() {
        // Expected that creation to fail, because this configuration is invalid.
        var exception = assertThrows(CompasException.class,
                () -> new XmlElementPojoMarshallerWrapper.Builder().withProperties("invalid-configs/marshaller-invalid-contextpath-config.yml").build());
        assertEquals(CREATION_ERROR_CODE, exception.getErrorCode());
    }

    @Test
    void marshaller_WhenInitializedWithInvalidXsdPath_ThenExceptionIsThrown() {
        // Expected that creation to fail, because this configuration is invalid.
        var exception = assertThrows(CompasException.class,
                () -> new XmlElementPojoMarshallerWrapper.Builder().withProperties("invalid-configs/marshaller-invalid-xsdpath-config.yml").build());
        assertEquals(RESOURCE_NOT_FOUND_ERROR_CODE, exception.getErrorCode());
    }

    private XmlElementAPojo createPOJO() {
        XmlElementAPojo aPojo = new XmlElementAPojo();
        aPojo.setId(UUID.randomUUID().toString());

        XmlElementBPojo bPojo = new XmlElementBPojo();
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