// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CompasErrorCodeTest {
    @Test
    void constructor_WhenConstructorCalled_ThenShouldThrowExceptionCauseForbidden() {
        assertThrows(UnsupportedOperationException.class, CompasErrorCode::new);
    }
}