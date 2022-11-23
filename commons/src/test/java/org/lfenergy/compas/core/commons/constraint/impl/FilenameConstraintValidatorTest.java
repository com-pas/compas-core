// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.constraint.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.constraint.FilenameValid;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilenameConstraintValidatorTest {
    private Validator validator;

    @BeforeEach
    void setupValidator() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void isValid_WhenCalledWithCorrectName_ThenNoViolations() {
        assertCorrectName("well.txt");
        assertCorrectName("station.scd");
    }

    private void assertCorrectName(String name) {
        var simplePojo = new SimplePojo(name);

        var violations = validator.validate(simplePojo);
        assertTrue(violations.isEmpty());
    }

    @Test
    void isValid_WhenCalledWithIncorrectName_ThenViolationFound() {
        assertIncorrectName(null);
        assertIncorrectName("");
        assertIncorrectName("test|.TXT");
        assertIncorrectName("test.T*T");
        assertIncorrectName("te?st.TXT");
        assertIncorrectName("test/test.TXT");
        assertIncorrectName("lpt1.out");
    }

    private void assertIncorrectName(String name) {
        var simplePojo = new SimplePojo(name);

        var violations = validator.validate(simplePojo);
        assertEquals(1, violations.size());
    }

    private record SimplePojo(@FilenameValid String name) {
    }
}
