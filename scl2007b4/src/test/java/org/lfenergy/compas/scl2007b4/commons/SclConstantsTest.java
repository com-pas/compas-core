// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2007b4.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SclConstantsTest {
    @Test
    void testShouldReturnNOKWhenConstructClassCauseForbidden() {
        assertThrows(UnsupportedOperationException.class, SclConstants::new);
    }
}