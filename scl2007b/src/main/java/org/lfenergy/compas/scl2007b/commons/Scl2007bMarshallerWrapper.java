// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2007b.commons;

import org.lfenergy.compas.core.commons.MarshallerWrapper;
import org.lfenergy.compas.scl2007b.model.SCL;

import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class Scl2007bMarshallerWrapper extends MarshallerWrapper<SCL> {
    public Scl2007bMarshallerWrapper(Unmarshaller jaxbUnmarshaller, Marshaller jaxbMarshaller) {
        super(jaxbUnmarshaller, jaxbMarshaller);
    }

    @Override
    protected Class<SCL> getResultClass() {
        return SCL.class;
    }

    public static class Builder extends MarshallerWrapper.Builder<Scl2007bMarshallerWrapper, SCL> {
        public Builder() {
            withProperties("scl2007b-marshaller-config.yml");
        }

        @Override
        protected Scl2007bMarshallerWrapper createMarshallerWrapper(Unmarshaller jaxbUnmarshaller,
                                                                    Marshaller jaxbMarshaller) {
            return new Scl2007bMarshallerWrapper(jaxbUnmarshaller, jaxbMarshaller);
        }
    }
}
