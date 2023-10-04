// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.ErrorResponse;

import jakarta.validation.ConstraintViolationException;
import jakarta.websocket.Session;
import jakarta.xml.bind.JAXBContext;
import java.io.StringReader;
import java.io.StringWriter;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.*;

public final class WebsocketSupport {
    WebsocketSupport() {
        throw new UnsupportedOperationException("WebsocketSupport class");
    }

    public static <T> String encode(T jaxbObject, Class<T> jaxbClass) {
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

    public static <T> T decode(String message, Class<T> jaxbClass) {
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

    public static void handleException(Session session, Throwable throwable) {
        var response = new ErrorResponse();
        if (throwable instanceof CompasException ce) {
            response.addErrorMessage(ce.getErrorCode(), ce.getMessage());
        } else if (throwable instanceof ConstraintViolationException cve) {
            cve.getConstraintViolations()
                    .forEach(constraintViolation ->
                            response.addErrorMessage(VALIDATION_ERROR,
                                    constraintViolation.getMessage(),
                                    constraintViolation.getPropertyPath().toString()));
        } else {
            response.addErrorMessage(WEBSOCKET_GENERAL_ERROR_CODE, throwable.getMessage());
        }
        session.getAsyncRemote().sendObject(response);
    }
}
