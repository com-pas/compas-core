// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.rest.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lfenergy.compas.core.commons.model.ErrorResponse;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.VALIDATION_ERROR;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    private static final Logger LOGGER = LogManager.getLogger(ConstraintViolationExceptionHandler.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        var response = new ErrorResponse();
        exception.getConstraintViolations()
                .forEach(constraintViolation ->
                        response.addErrorMessage(VALIDATION_ERROR,
                                constraintViolation.getMessage(),
                                constraintViolation.getPropertyPath().toString()));

        LOGGER.debug("Constraint violation exception occurred.", exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}
