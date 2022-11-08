// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.lfenergy.compas.core.commons.exception.CompasException;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.xml.bind.JAXBContext;
import java.io.StringWriter;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.WEBSOCKET_ENCODER_ERROR_CODE;

/**
 * Simple abstract class to save some boilerplate code with common implementations.
 *
 * @param <T> The type to encode.
 */
public abstract class AbstractJaxbEncoder<T> implements Encoder.Text<T> {
    private final Class<T> jaxbClass;

    public AbstractJaxbEncoder(Class<T> jaxbClass) {
        this.jaxbClass = jaxbClass;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing.
    }

    @Override
    public String encode(T jaxbObject) {
        try {
            var jaxbContext = JAXBContext.newInstance(jaxbClass);
            var marshaller = jaxbContext.createMarshaller();

            var st = new StringWriter();
            marshaller.marshal(jaxbObject, st);
            return st.toString();
        } catch (Exception exp) {
            throw new CompasException(WEBSOCKET_ENCODER_ERROR_CODE,
                    "Error marshalling to string from class '" + jaxbClass.getName() + "' for Websockets.",
                    exp);
        }
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
