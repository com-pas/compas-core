// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesTest {
    @Test
    void constructor_WhenConstructorCalled_ThenShouldThrowExceptionCauseForbidden() {
        assertThrows(UnsupportedOperationException.class, Resources::new);
    }

    @Test
    void getResource_WhenCalledForExistingResource_ThenURLToResourceIsReturned() {
        var testResource = "xml-element-marshaller-config.yml";

        var result = Resources.getResource(testResource);

        assertTrue(result.isPresent());
        assertTrue(result.get().toString().endsWith("/" + testResource));
    }

    @Test
    void getResource_WhenCalledForNonExistingResource_ThenEmptyOptionalReturned() {
        var testResource = "unknown-marshaller-config.yml";

        var result = Resources.getResource(testResource);

        assertFalse(result.isPresent());
    }
}