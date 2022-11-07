// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.ErrorResponse;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.WEBSOCKET_GENERAL_ERROR_CODE;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebsocketHandlerTest {

    @Test
    void execute_WhenCalledSuccessful_ThenResponseSendToWebsocket() {
        var message = "Some message";

        var session = Mockito.mock(Session.class);
        var async = Mockito.mock(RemoteEndpoint.Async.class);
        when(session.getAsyncRemote()).thenReturn(async);

        // Execute the Handler.
        new WebsocketHandler<String>().execute(session, () -> message);

        verify(session, times(1)).getAsyncRemote();
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(async, times(1)).sendObject(captor.capture());
        var response = captor.getValue();
        assertEquals(message, response);
    }

    @Test
    void execute_WhenCalledAndCompasExceptionThrownByExecutor_ThenErrorResponseReturned() {
        var errorCode = "SOME-CODE";
        var errorMessage = "Some error";

        var session = Mockito.mock(Session.class);
        var async = Mockito.mock(RemoteEndpoint.Async.class);
        when(session.getAsyncRemote()).thenReturn(async);

        // Execute the Handler.
        new WebsocketHandler<String>().execute(session, () -> {
            throw new CompasException(errorCode, errorMessage);
        });

        verify(session, times(1)).getAsyncRemote();
        ArgumentCaptor<ErrorResponse> captor = ArgumentCaptor.forClass(ErrorResponse.class);
        verify(async, times(1)).sendObject(captor.capture());
        var response = captor.getValue();
        assertEquals(1, response.getErrorMessages().size());
        var message = response.getErrorMessages().get(0);
        assertEquals(errorCode, message.getCode());
        assertEquals(errorMessage, message.getMessage());
    }

    @Test
    void execute_WhenCalledAndRuntimeExceptionThrownByExecutor_ThenErrorResponseReturned() {
        var errorMessage = "Some error";

        var session = Mockito.mock(Session.class);
        var async = Mockito.mock(RemoteEndpoint.Async.class);
        when(session.getAsyncRemote()).thenReturn(async);

        // Execute the Handler.
        new WebsocketHandler<String>().execute(session, () -> {
            throw new RuntimeException(errorMessage);
        });

        verify(session, times(1)).getAsyncRemote();
        ArgumentCaptor<ErrorResponse> captor = ArgumentCaptor.forClass(ErrorResponse.class);
        verify(async, times(1)).sendObject(captor.capture());
        var response = captor.getValue();
        assertEquals(1, response.getErrorMessages().size());
        var message = response.getErrorMessages().get(0);
        assertEquals(WEBSOCKET_GENERAL_ERROR_CODE, message.getCode());
        assertEquals(errorMessage, message.getMessage());
    }
}
