// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.rest.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.lfenergy.compas.core.commons.model.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CompasExceptionHandler implements ExceptionMapper<CompasException> {
    private static final Logger LOGGER = LogManager.getLogger(CompasExceptionHandler.class);

    @Override
    public Response toResponse(CompasException exception) {
        var response = new ErrorResponse();
        response.addErrorMessage(exception.getErrorCode(), exception.getMessage());

        LOGGER.warn("Compas exception occurred.", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
    }
}
