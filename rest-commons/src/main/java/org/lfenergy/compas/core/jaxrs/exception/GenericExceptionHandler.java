// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.jaxrs.exception;

import org.lfenergy.compas.core.commons.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.UNKNOWN_EXCEPTION_ERROR;

@Provider
public class GenericExceptionHandler implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionHandler.class);

    public static final String ERROR_MESSAGE = "Unknown exception occurred.";

    @Override
    public Response toResponse(Throwable throwable) {
        var response = new ErrorResponse();
        response.addErrorMessage(UNKNOWN_EXCEPTION_ERROR, ERROR_MESSAGE);

        LOGGER.warn(ERROR_MESSAGE, throwable);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }
}
