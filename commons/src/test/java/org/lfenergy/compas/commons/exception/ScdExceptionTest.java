// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.commons.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScdExceptionTest {
    @Test
    void constructor_WhenCalledWithOnlyMessage_ThenMessageCanBeRetrieved() {
        String expectedMessage = "The message";
        ScdException exception = new ScdException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

}