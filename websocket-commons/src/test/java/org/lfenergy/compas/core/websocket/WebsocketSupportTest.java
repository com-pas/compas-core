// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.ErrorResponse;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.core.commons.CommonConstants.COMPAS_COMMONS_V1_NS_URI;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.*;
import static org.lfenergy.compas.core.websocket.WebsocketSupport.*;
import static org.mockito.Mockito.*;

class WebsocketSupportTest {
    @Test
    void constructor_WhenConstructorCalled_ThenShouldThrowExceptionCauseForbidden() {
        assertThrows(UnsupportedOperationException.class, WebsocketSupport::new);
    }

    @Test
    void encode_WhenCalledWithErrorResponse_ThenXMLStringReturned() {
        var errorCode = "ERR-0001";
        var errorMessage = "Error Message";
        var errorResponse = new ErrorResponse();
        errorResponse.addErrorMessage(errorCode, errorMessage);

        var result = encode(errorResponse, ErrorResponse.class);

        var expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<compas-commons:ErrorResponse xmlns:compas-commons=\"" + COMPAS_COMMONS_V1_NS_URI + "\">" +
                "<compas-commons:ErrorMessage>" +
                "<compas-commons:Code>" + errorCode + "</compas-commons:Code>" +
                "<compas-commons:Message>" + errorMessage + "</compas-commons:Message>" +
                "</compas-commons:ErrorMessage>" +
                "</compas-commons:ErrorResponse>";
        assertEquals(expectedResult, result);
    }

    @Test
    void encode_WhenCalledWithNoJaxbObject_ThenExceptionThrown() {
        var exception = assertThrows(CompasException.class, () -> encode("Some Non JAXB String", String.class));
        assertEquals(WEBSOCKET_ENCODER_ERROR_CODE, exception.getErrorCode());
        assertEquals(CompasException.class, exception.getClass());
    }

    @Test
    void decode_WhenCalledWithCorrectXML_ThenObjectReturned() {
        var errorCode = "ERR-0001";
        var errorMessage = "Error Message";

        var xmlMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<compas-commons:ErrorResponse xmlns:compas-commons=\"" + COMPAS_COMMONS_V1_NS_URI + "\">" +
                "<compas-commons:ErrorMessage>" +
                "<compas-commons:Code>" + errorCode + "</compas-commons:Code>" +
                "<compas-commons:Message>" + errorMessage + "</compas-commons:Message>" +
                "</compas-commons:ErrorMessage>" +
                "</compas-commons:ErrorResponse>";

        var result = decode(xmlMessage, ErrorResponse.class);

        assertNotNull(result);
        assertEquals(1, result.getErrorMessages().size());
        var message = result.getErrorMessages().get(0);
        assertEquals(errorCode, message.getCode());
        assertEquals(errorMessage, message.getMessage());
    }


    @Test
    void decode_WhenCalledWithInvalidXML_ThenExceptionThrown() {
        var xmlMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<compas-commons:InvalidResponse xmlns:compas-commons=\"" + COMPAS_COMMONS_V1_NS_URI + "\">" +
                "</compas-commons:InvalidResponse>";

        var exception = assertThrows(CompasException.class, () -> decode(xmlMessage, ErrorResponse.class));
        assertEquals(WEBSOCKET_DECODER_ERROR_CODE, exception.getErrorCode());
        assertEquals(CompasException.class, exception.getClass());
    }

    @Test
    void handleException_WhenCalledWithCompasException_ThenErrorResponseSendToSession() {
        var errorCode = "ERR-0001";
        var errorMessage = "Error Message";
        var session = mockSession();

        handleException(session, new CompasException(errorCode, errorMessage));

        verifyErrorResponse(session, errorCode, errorMessage);
    }

    @Test
    void handleException_WhenCalledWithRuntimeException_ThenErrorResponseSendToSession() {
        var errorMessage = "Error Message";
        var session = mockSession();

        handleException(session, new RuntimeException(errorMessage));

        verifyErrorResponse(session, WEBSOCKET_GENERAL_ERROR_CODE, errorMessage);
    }

    private Session mockSession() {
        var session = Mockito.mock(Session.class);
        var async = Mockito.mock(RemoteEndpoint.Async.class);
        when(session.getAsyncRemote()).thenReturn(async);
        return session;
    }

    private void verifyErrorResponse(Session session, String errorCode, String errorMessage) {
        verify(session, times(1)).getAsyncRemote();
        ArgumentCaptor<ErrorResponse> captor = ArgumentCaptor.forClass(ErrorResponse.class);
        verify(session.getAsyncRemote(), times(1)).sendObject(captor.capture());
        var response = captor.getValue();
        assertEquals(1, response.getErrorMessages().size());
        var message = response.getErrorMessages().get(0);
        assertEquals(errorCode, message.getCode());
        assertEquals(errorMessage, message.getMessage());
    }
}
