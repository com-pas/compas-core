// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.rest.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.lfenergy.compas.core.commons.model.ErrorResponse;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashSet;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.VALIDATION_ERROR;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConstraintViolationExceptionHandlerTest {
    private static final String MESSAGE = "Message";
    private static final String PROPERTY = "PropertyPath";

    @Mock
    private ConstraintViolation<String> constraintViolation;
    @Mock
    private Path propertyPath;

    @Test
    void toResponse_WhenCalledWithConstraintViolations_ThenInternalServerErrorReturnedWithBody() {
        when(propertyPath.toString()).thenReturn(PROPERTY);
        when(constraintViolation.getMessage()).thenReturn(MESSAGE);
        when(constraintViolation.getPropertyPath()).thenReturn(propertyPath);

        var constraintViolations = new HashSet<ConstraintViolation<String>>();
        constraintViolations.add(constraintViolation);

        var exception = new ConstraintViolationException(constraintViolations);
        var handler = new ConstraintViolationExceptionHandler();

        try (var result = handler.toResponse(exception)) {
            assertEquals(BAD_REQUEST.getStatusCode(), result.getStatus());
            var errorMessage = ((ErrorResponse) result.getEntity()).getErrorMessages().get(0);
            assertEquals(VALIDATION_ERROR, errorMessage.getCode());
            assertEquals(MESSAGE, errorMessage.getMessage());
            assertEquals(PROPERTY, errorMessage.getProperty());
            verifyNoMoreInteractions(constraintViolation, propertyPath);
        }
    }

    @Test
    void toResponse_WhenCalledWithoutConstraintViolations_ThenInternalServerErrorReturnedWithoutMessages() {
        var constraintViolations = new HashSet<ConstraintViolation<String>>();
        var exception = new ConstraintViolationException(constraintViolations);
        var handler = new ConstraintViolationExceptionHandler();

        try (var result = handler.toResponse(exception)) {
            assertEquals(BAD_REQUEST.getStatusCode(), result.getStatus());
            var response = (ErrorResponse) result.getEntity();
            assertTrue(response.getErrorMessages().isEmpty());
            verifyNoInteractions(constraintViolation, propertyPath);
        }
    }
}
