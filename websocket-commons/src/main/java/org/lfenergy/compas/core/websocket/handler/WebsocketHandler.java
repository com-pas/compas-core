// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket.handler;

import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.WEBSOCKET_GENERAL_ERROR_CODE;

/**
 * Simple Websocket Handler to handle the result from an executor being called and send this result to the Websocket
 * Client. If an Exception is thrown an {@link ErrorResponse} is sent to the Websocket Client.
 *
 * @param <T> The type of response returned from the Executor and send to the Websocket client.
 */
public class WebsocketHandler<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketHandler.class);

    public void execute(Session session, EventExecutor<T> executor) {
        try {
            LOGGER.debug("Executing executor to retrieve response");
            var result = executor.execute();
            session.getAsyncRemote().sendObject(result);
        } catch (RuntimeException re) {
            handleException(session, re);
        }
    }

    private void handleException(Session session, RuntimeException re) {
        var response = new ErrorResponse();
        if (re instanceof CompasException) {
            LOGGER.info("Handling CompasException thrown by Executor!", re);
            response.addErrorMessage(((CompasException) re).getErrorCode(), re.getMessage());
        } else {
            LOGGER.info("Handling RuntimeException thrown by Executor!", re);
            response.addErrorMessage(WEBSOCKET_GENERAL_ERROR_CODE, re.getMessage());
        }
        session.getAsyncRemote().sendObject(response);
    }

    @FunctionalInterface
    public interface EventExecutor<T> {
        T execute();
    }
}
