// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lfenergy.compas.core.commons.model.ErrorResponse;

import jakarta.websocket.Session;

import static org.lfenergy.compas.core.websocket.WebsocketSupport.handleException;

/**
 * Simple Websocket Handler to handle the result from an executor being called and send this result to the Websocket
 * Client. If an Exception is thrown an {@link ErrorResponse} is sent to the Websocket Client.
 *
 * @param <T> The type of response returned from the Executor and send to the Websocket client.
 */
public class WebsocketHandler<T> {
    private static final Logger LOGGER = LogManager.getLogger(WebsocketHandler.class);

    public void execute(Session session, EventExecutor<T> executor) {
        try {
            LOGGER.debug("Executing executor to retrieve response");
            var result = executor.execute();
            session.getAsyncRemote().sendObject(result);
        } catch (RuntimeException exp) {
            LOGGER.info("Exception occurred during handling the websocket request", exp);
            handleException(session, exp);
        }
    }

    @FunctionalInterface
    public interface EventExecutor<T> {
        T execute();
    }
}
