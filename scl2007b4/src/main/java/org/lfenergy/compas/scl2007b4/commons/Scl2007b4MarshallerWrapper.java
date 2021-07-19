// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2007b4.commons;

import org.lfenergy.compas.commons.MarshallerWrapper;
import org.lfenergy.compas.scl2007b4.model.SCL;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Scl2007b4MarshallerWrapper extends MarshallerWrapper<SCL> {
    public Scl2007b4MarshallerWrapper(Unmarshaller jaxbUnmarshaller, Marshaller jaxbMarshaller) {
        super(jaxbUnmarshaller, jaxbMarshaller);
    }

    @Override
    protected Class<SCL> getResultClass() {
        return SCL.class;
    }

    public static class Builder extends MarshallerWrapper.Builder<Scl2007b4MarshallerWrapper, SCL> {
        public Builder() {
            withProperties("scl2007b4-marshaller-config.yml");
        }

        @Override
        protected Scl2007b4MarshallerWrapper createMarshallerWrapper(Unmarshaller jaxbUnmarshaller,
                                                                     Marshaller jaxbMarshaller) {
            return new Scl2007b4MarshallerWrapper(jaxbUnmarshaller, jaxbMarshaller);
        }
    }
}
