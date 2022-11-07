// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.ErrorResponse;

import javax.xml.bind.JAXBContext;
import java.io.StringReader;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.WEBSOCKET_DECODER_ERROR_CODE;

public class ErrorResponseDecoder extends AbstractDecoder<ErrorResponse> {
    @Override
    public boolean willDecode(String message) {
        return (message != null);
    }

    @Override
    public ErrorResponse decode(String message) {
        try {
            var jaxbContext = JAXBContext.newInstance(ErrorResponse.class);
            var unmarshaller = jaxbContext.createUnmarshaller();
            var reader = new StringReader(message);
            return (ErrorResponse) unmarshaller.unmarshal(reader);
        } catch (Exception exp) {
            throw new CompasException(WEBSOCKET_DECODER_ERROR_CODE,
                    "Error unmarshalling Error Response from Websockets.",
                    exp);
        }
    }
}
