// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.rest.exception;

import org.lfenergy.compas.core.rest.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.VALIDATION_ERROR;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionHandler.class);

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
