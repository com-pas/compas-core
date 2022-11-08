// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.ErrorResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.core.commons.CommonConstants.COMPAS_COMMONS_V1_NS_URI;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.WEBSOCKET_ENCODER_ERROR_CODE;

class ErrorResponseEncoderTest {
    private ErrorResponseEncoder encoder;

    @BeforeEach
    void init() {
        encoder = new ErrorResponseEncoder();
        encoder.init(null);
    }

    @Test
    void encode_WhenCalledWithRequest_ThenRequestConvertedToString() {
        var errorCode = "SDS-0001";
        var errorMessage = "Some message";

        var request = new ErrorResponse();
        request.addErrorMessage(errorCode, errorMessage);

        var result = encoder.encode(request);

        var expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<compas-commons:ErrorResponse xmlns:compas-commons=\"" + COMPAS_COMMONS_V1_NS_URI + "\">" +
                "<compas-commons:ErrorMessage>" +
                "<compas-commons:Code>" + errorCode + "</compas-commons:Code>" +
                "<compas-commons:Message>" + errorMessage + "</compas-commons:Message>" +
                "</compas-commons:ErrorMessage>" +
                "</compas-commons:ErrorResponse>";
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void encode_WhenCalledWithNull_ThenExceptionThrown() {
        var exception = assertThrows(CompasException.class, () -> encoder.encode(null));
        assertEquals(WEBSOCKET_ENCODER_ERROR_CODE, exception.getErrorCode());
        assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
    }

    @AfterEach
    void destroy() {
        encoder.destroy();
    }
}
