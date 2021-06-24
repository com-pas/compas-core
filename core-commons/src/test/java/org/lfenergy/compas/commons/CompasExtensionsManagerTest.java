// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl.extensions.TSclFileType;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.scl.extensions.common.CompasExtensionsConstants.COMPAS_SCL_EXTENSION_TYPE;
import static org.lfenergy.compas.scl.extensions.common.CompasExtensionsField.SCL_NAME_EXTENSION;
import static org.lfenergy.compas.util.ReadTestFile.readSCL;

class CompasExtensionsManagerTest {
    private CompasExtensionsManager manager = new CompasExtensionsManager();

    @Test
    void getCompasPrivate_WhenCalledNullPassed_ThenNoCompasPrivateReturned() {
        var compasPrivate = manager.getCompasPrivate(null);

        assertFalse(compasPrivate.isPresent());
    }

    @Test
    void getCompasPrivate_WhenCalledWithPrivate_ThenCompasPrivateReturned() throws Exception {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);

        assertTrue(compasPrivate.isPresent());
    }

    @Test
    void getCompasPrivate_WhenCalledWithoutPrivate_ThenNoCompasPrivateReturned() throws Exception {
        var scl = readSCL("scl_without_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);

        assertFalse(compasPrivate.isPresent());
    }

    @Test
    void getCompasElement_WhenCalledNullPassed_ThenNoNameReturned() {
        var compasElement = manager.getCompasElement(null, SCL_NAME_EXTENSION);

        assertFalse(compasElement.isPresent());
    }

    @Test
    void getCompasElement_WhenCalledWithoutName_ThenNoNameReturned() throws Exception {
        var scl = readSCL("scl_without_name_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        assertTrue(compasPrivate.isPresent());
        var compasElement = manager.getCompasElement(compasPrivate.get(), SCL_NAME_EXTENSION);

        assertFalse(compasElement.isPresent());
    }

    @Test
    void getCompasElement_WhenCalledWithName_ThenNameReturned() throws Exception {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        assertTrue(compasPrivate.isPresent());
        var compasElement = manager.getCompasElement(compasPrivate.get(), SCL_NAME_EXTENSION);

        assertTrue(compasElement.isPresent());
        assertEquals("project", compasElement.get().getValue().toString());
    }

    @Test
    void getCompasSclName_WhenCalledNullPassed_ThenNoNameReturned() {
        var value = manager.getCompasSclName(null);

        assertFalse(value.isPresent());
    }

    @Test
    void getCompasSclName_WhenCalledWithoutName_ThenNoNameReturned() throws Exception {
        var scl = readSCL("scl_without_name_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        assertTrue(compasPrivate.isPresent());
        var value = manager.getCompasSclName(compasPrivate.get());

        assertFalse(value.isPresent());
    }

    @Test
    void getCompasSclName_WhenCalledWithName_ThenNameReturned() throws Exception {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        assertTrue(compasPrivate.isPresent());
        var value = manager.getCompasSclName(compasPrivate.get());

        assertTrue(value.isPresent());
        assertEquals("project", value.get());
    }

    @Test
    void getCompasSclFileType_WhenCalledNullPassed_ThenNoTypeReturned() {
        var value = manager.getCompasSclFileType(null);

        assertFalse(value.isPresent());
    }

    @Test
    void getCompasSclFileType_WhenCalledWithoutType_ThenNoTypeReturned() throws Exception {
        var scl = readSCL("scl_without_type_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        assertTrue(compasPrivate.isPresent());
        var value = manager.getCompasSclFileType(compasPrivate.get());

        assertFalse(value.isPresent());
    }

    @Test
    void getCompasElement_WhenCalledWithType_ThenTypeReturned() throws Exception {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        assertTrue(compasPrivate.isPresent());
        var value = manager.getCompasSclFileType(compasPrivate.get());

        assertTrue(value.isPresent());
        assertEquals(TSclFileType.CID, value.get());
    }

    @Test
    void createCompasPrivate_WhenCalled_ThenNewCompasPrivateReturned() {
        var compasPrivate = manager.createCompasPrivate();

        assertNotNull(compasPrivate);
        assertEquals(COMPAS_SCL_EXTENSION_TYPE, compasPrivate.getType());
    }
}