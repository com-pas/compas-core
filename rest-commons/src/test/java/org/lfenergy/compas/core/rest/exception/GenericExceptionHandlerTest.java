// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.rest.exception;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.model.ErrorResponse;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.UNKNOWN_EXCEPTION_ERROR;
import static org.lfenergy.compas.core.rest.exception.GenericExceptionHandler.ERROR_MESSAGE;

class GenericExceptionHandlerTest {
    @Test
    void toResponse_WhenCalledWithException_ThenInternalServerErrorReturnedWithBody() {
        var exception = new Exception("Some message that will only be logged");
        var handler = new GenericExceptionHandler();

        try (var result = handler.toResponse(exception)) {
            assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), result.getStatus());
            var errorMessage = ((ErrorResponse) result.getEntity()).getErrorMessages().get(0);
            assertEquals(UNKNOWN_EXCEPTION_ERROR, errorMessage.getCode());
            assertEquals(ERROR_MESSAGE, errorMessage.getMessage());
            assertNull(errorMessage.getProperty());
        }
    }
}
