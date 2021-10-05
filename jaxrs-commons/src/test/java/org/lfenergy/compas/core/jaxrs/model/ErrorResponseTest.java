// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.jaxrs.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ErrorResponseTest extends AbstractPojoTester {
    private static final String MESSAGE = "Message";
    private static final String CODE = "CODE";
    private static final String PROPERTY = "PropertyPath";

    @Override
    protected Class<?> getClassToBeTested() {
        return ErrorResponse.class;
    }

    @Test
    void addErrorMessage_WhenCalledWithPartialParameter_ThenCorrectFieldsAreFilled() {
        var response = new ErrorResponse();
        response.addErrorMessage(CODE, MESSAGE);

        assertEquals(1, response.getErrorMessages().size());
        var message = response.getErrorMessages().get(0);
        assertEquals(CODE, message.getCode());
        assertEquals(MESSAGE, message.getMessage());
        assertNull(message.getProperty());
    }

    @Test
    void addErrorMessage_WhenCalledWithAllParameter_ThenCorrectFieldsAreFilled() {
        var response = new ErrorResponse();
        response.addErrorMessage(CODE, MESSAGE, PROPERTY);

        assertEquals(1, response.getErrorMessages().size());
        var message = response.getErrorMessages().get(0);
        assertEquals(CODE, message.getCode());
        assertEquals(MESSAGE, message.getMessage());
        assertEquals(PROPERTY, message.getProperty());
    }
}