// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ErrorMessageTest extends AbstractPojoTester {
    private static final String MESSAGE = "Message";
    private static final String CODE = "CODE";
    private static final String PROPERTY = "PropertyPath";

    @Override
    protected Class<?> getClassToBeTested() {
        return ErrorMessage.class;
    }

    @Test
    void constructor_WhenCreateWithPartialParameter_ThenCorrectFieldsAreFilled() {
        var result = new ErrorMessage(CODE, MESSAGE);

        assertEquals(CODE, result.getCode());
        assertEquals(MESSAGE, result.getMessage());
        assertNull(result.getProperty());
    }

    @Test
    void constructor_WhenCreateWithAllParameter_ThenCorrectFieldsAreFilled() {
        var result = new ErrorMessage(CODE, MESSAGE, PROPERTY);

        assertEquals(CODE, result.getCode());
        assertEquals(MESSAGE, result.getMessage());
        assertEquals(PROPERTY, result.getProperty());
    }
}