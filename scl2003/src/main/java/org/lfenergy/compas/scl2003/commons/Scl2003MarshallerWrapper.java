// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2003.commons;

import org.lfenergy.compas.commons.MarshallerWrapper;
import org.lfenergy.compas.scl2003.model.SCL;

import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Scl2003MarshallerWrapper extends MarshallerWrapper<SCL> {
    public Scl2003MarshallerWrapper(Unmarshaller jaxbUnmarshaller, Marshaller jaxbMarshaller) {
        super(jaxbUnmarshaller, jaxbMarshaller);
    }

    @Override
    protected Class<SCL> getResultClass() {
        return SCL.class;
    }

    public static class Builder extends MarshallerWrapper.Builder<Scl2003MarshallerWrapper, SCL> {
        public Builder() {
            withProperties("scl2003-marshaller-config.yml");
        }

        @Override
        protected Scl2003MarshallerWrapper createMarshallerWrapper(Unmarshaller jaxbUnmarshaller,
                                                                   Marshaller jaxbMarshaller) {
            return new Scl2003MarshallerWrapper(jaxbUnmarshaller, jaxbMarshaller);
        }
    }
}
