package org.lfenergy.compas.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonConstantsTest {

    @Test
    void testShouldReturnNOKWhenConstructClassCauseForbidden() {
        assertThrows(UnsupportedOperationException.class, () -> new CommonConstants());
    }

}