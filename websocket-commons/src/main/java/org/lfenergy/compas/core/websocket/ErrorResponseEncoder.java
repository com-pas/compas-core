// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.ErrorResponse;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.xml.bind.JAXBContext;
import java.io.StringWriter;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.WEBSOCKET_ENCODER_ERROR_CODE;

public class ErrorResponseEncoder implements Encoder.Text<ErrorResponse> {
    @Override
    public void init(EndpointConfig endpointConfig) {
        // do nothing.
    }

    @Override
    public String encode(ErrorResponse response) {
        try {
            var jaxbContext = JAXBContext.newInstance(ErrorResponse.class);
            var marshaller = jaxbContext.createMarshaller();

            var st = new StringWriter();
            marshaller.marshal(response, st);
            return st.toString();
        } catch (Exception exp) {
            throw new CompasException(WEBSOCKET_ENCODER_ERROR_CODE,
                    "Error marshalling Error Response to Websockets.",
                    exp);
        }
    }

    @Override
    public void destroy() {
        // do nothing.
    }
}
