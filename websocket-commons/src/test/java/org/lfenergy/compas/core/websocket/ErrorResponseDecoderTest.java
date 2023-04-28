// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.exception.CompasException;

import jakarta.xml.bind.UnmarshalException;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.core.commons.CommonConstants.COMPAS_COMMONS_V1_NS_URI;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.WEBSOCKET_DECODER_ERROR_CODE;

class ErrorResponseDecoderTest {
    private ErrorResponseDecoder decoder;

    @BeforeEach
    void init() {
        decoder = new ErrorResponseDecoder();
        decoder.init(null);
    }

    @Test
    void willDecode_WhenCalledWithString_ThenTrueReturned() {
        assertTrue(decoder.willDecode(""));
        assertTrue(decoder.willDecode("Some text"));
    }

    @Test
    void willDecode_WhenCalledWithNull_ThenFalseReturned() {
        assertFalse(decoder.willDecode(null));
    }

    @Test
    void decode_WhenCalledWithCorrectRequestXML_ThenStringConvertedToObject() {
        var errorCode = "SDS-0001";
        var errorMessage = "Some message";
        var body = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<compas-commons:ErrorResponse xmlns:compas-commons=\"" + COMPAS_COMMONS_V1_NS_URI + "\">" +
                "<compas-commons:ErrorMessage>" +
                "<compas-commons:Code>" + errorCode + "</compas-commons:Code>" +
                "<compas-commons:Message>" + errorMessage + "</compas-commons:Message>" +
                "</compas-commons:ErrorMessage>" +
                "</compas-commons:ErrorResponse>";

        var result = decoder.decode(body);

        assertNotNull(result);
        assertEquals(1, result.getErrorMessages().size());
        var message = result.getErrorMessages().get(0);
        assertEquals(errorCode, message.getCode());
        assertEquals(errorMessage, message.getMessage());
    }

    @Test
    void decode_WhenCalledWithWrongXMLType_ThenExceptionThrown() {
        var message = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<sds:InvalidRequest xmlns:sds=\"" + COMPAS_COMMONS_V1_NS_URI + "\">"
                + "</sds:InvalidRequest>";

        var exception = assertThrows(CompasException.class, () -> decoder.decode(message));
        assertEquals(WEBSOCKET_DECODER_ERROR_CODE, exception.getErrorCode());
        assertEquals(UnmarshalException.class, exception.getCause().getClass());
    }

    @Test
    void decode_WhenCalledWithInCorrectMessage_ThenExceptionThrown() {
        var message = "Incorrect Message";

        var exception = assertThrows(CompasException.class, () -> decoder.decode(message));
        assertEquals(WEBSOCKET_DECODER_ERROR_CODE, exception.getErrorCode());
        assertEquals(UnmarshalException.class, exception.getCause().getClass());
    }

    @AfterEach
    void destroy() {
        decoder.destroy();
    }
}
