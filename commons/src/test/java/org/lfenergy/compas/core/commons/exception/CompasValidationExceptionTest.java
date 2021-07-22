// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.NO_CLASS_LOADER_ERROR_CODE;

class CompasValidationExceptionTest {
    @Test
    void constructor_WhenCalledWithOnlyMessage_ThenMessageCanBeRetrieved() {
        var expectedMessage = "The message";
        var exception = new CompasValidationException(NO_CLASS_LOADER_ERROR_CODE, expectedMessage);

        assertEquals(NO_CLASS_LOADER_ERROR_CODE, exception.getErrorCode());
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void constructor_WhenCalledWithMessageAndThrowable_ThenMessageAndThrowableCanBeRetrieved() {
        var expectedMessage = "The message";
        var expectedThrowable = new NullPointerException();
        var exception = new CompasValidationException(NO_CLASS_LOADER_ERROR_CODE, expectedMessage, expectedThrowable);

        assertEquals(NO_CLASS_LOADER_ERROR_CODE, exception.getErrorCode());
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedThrowable, exception.getCause());
    }
}