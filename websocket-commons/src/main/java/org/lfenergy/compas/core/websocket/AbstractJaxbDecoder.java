// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.lfenergy.compas.core.commons.exception.CompasException;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import javax.xml.bind.JAXBContext;
import java.io.StringReader;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.WEBSOCKET_DECODER_ERROR_CODE;

/**
 * Simple abstract class to save some boilerplate code with common implementations.
 *
 * @param <T> The type to decode.
 */
public abstract class AbstractJaxbDecoder<T> implements Decoder.Text<T> {
    private final Class<T> jaxbClass;

    protected AbstractJaxbDecoder(Class<T> jaxbClass) {
        this.jaxbClass = jaxbClass;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing.
    }

    @Override
    public boolean willDecode(String message) {
        return (message != null);
    }

    @Override
    public T decode(String message) {
        try {
            var jaxbContext = JAXBContext.newInstance(jaxbClass);
            var unmarshaller = jaxbContext.createUnmarshaller();
            var reader = new StringReader(message);
            return jaxbClass.cast(unmarshaller.unmarshal(reader));
        } catch (Exception exp) {
            throw new CompasException(WEBSOCKET_DECODER_ERROR_CODE,
                    "Error unmarshalling to class '" + jaxbClass.getName() + "' from Websockets.",
                    exp);
        }
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
