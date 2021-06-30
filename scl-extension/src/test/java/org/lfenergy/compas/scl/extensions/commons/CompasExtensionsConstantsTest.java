package org.lfenergy.compas.scl.extensions.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CompasExtensionsConstantsTest {
    @Test
    void testShouldReturnNOKWhenConstructClassCauseForbidden() {
        assertThrows(UnsupportedOperationException.class, CompasExtensionsConstants::new);
    }

}