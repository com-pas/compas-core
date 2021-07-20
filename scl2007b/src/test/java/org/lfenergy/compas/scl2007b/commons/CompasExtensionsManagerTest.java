// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2007b.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl.extensions.model.TSclFileType;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsConstants.COMPAS_SCL_EXTENSION_TYPE;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_FILETYPE_EXTENSION;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_NAME_EXTENSION;
import static org.lfenergy.compas.scl2007b.util.ReadTestFile.readSCL;

class CompasExtensionsManagerTest {
    private static final String COMPAS_PRIVATE_NOT_FOUND = "No Compas Private Element found";

    private final CompasExtensionsManager manager = new CompasExtensionsManager();

    @Test
    void getCompasPrivate_WhenCalledWithPrivate_ThenCompasPrivateReturned() {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);

        assertTrue(compasPrivate.isPresent());
    }

    @Test
    void getCompasPrivate_WhenCalledNullPassed_ThenNoCompasPrivateReturned() {
        var compasPrivate = manager.getCompasPrivate(null);

        assertFalse(compasPrivate.isPresent());
    }

    @Test
    void getCompasPrivate_WhenCalledWithoutPrivate_ThenNoCompasPrivateReturned() {
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
    void getCompasElement_WhenCalledWithoutSclName_ThenNoSclNameReturned() {
        var scl = readSCL("scl_without_sclname_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var compasElement = manager.getCompasElement(tPrivate, SCL_NAME_EXTENSION);

            assertFalse(compasElement.isPresent());
        }, () -> fail(COMPAS_PRIVATE_NOT_FOUND));
    }

    @Test
    void getCompasElement_WhenCalledWithSclName_ThenSclNameReturned() {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var compasElement = manager.getCompasElement(tPrivate, SCL_NAME_EXTENSION);

            assertTrue(compasElement.isPresent());
            assertEquals("project", compasElement.get().getValue().toString());
        }, () -> fail(COMPAS_PRIVATE_NOT_FOUND));
    }

    @Test
    void getCompasElement_WhenCalledWithoutFileType_ThenNoFileTypeReturned() {
        var scl = readSCL("scl_without_filetype_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var compasElement = manager.getCompasElement(tPrivate, SCL_FILETYPE_EXTENSION);

            assertFalse(compasElement.isPresent());
        }, () -> fail(COMPAS_PRIVATE_NOT_FOUND));
    }

    @Test
    void getCompasElement_WhenCalledWithFileType_ThenFileTypeReturned() {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var compasElement = manager.getCompasElement(tPrivate, SCL_FILETYPE_EXTENSION);

            assertTrue(compasElement.isPresent());
            assertEquals(TSclFileType.CID, compasElement.get().getValue());
        }, () -> fail(COMPAS_PRIVATE_NOT_FOUND));
    }

    @Test
    void getCompasSclName_WhenCalledNullPassed_ThenNoNameReturned() {
        var value = manager.getCompasSclName(null);

        assertFalse(value.isPresent());
    }

    @Test
    void getCompasSclName_WhenCalledWithsclName_TheSclNameReturned() {
        var scl = readSCL("scl_with_compas_private.scd");
        var compasPrivate = manager.getCompasPrivate(scl);

        compasPrivate.ifPresentOrElse(tPrivate -> {
            var sclName = manager.getCompasSclName(tPrivate);

            assertTrue(sclName.isPresent());
            assertEquals("project", sclName.get());
        }, () -> fail(COMPAS_PRIVATE_NOT_FOUND));
    }

    @Test
    void getCompasSclName_WhenCalledWithNosclName_TheEmptyOptionalReturned() {
        var scl = readSCL("scl_without_sclname_compas_private.scd");
        var compasPrivate = manager.getCompasPrivate(scl);

        compasPrivate.ifPresentOrElse(tPrivate -> {
            var sclName = manager.getCompasSclName(tPrivate);

            assertFalse(sclName.isPresent());
        }, () -> fail(COMPAS_PRIVATE_NOT_FOUND));
    }

    @Test
    void getCompasSclFileType_WhenCalledNullPassed_ThenNoTypeReturned() {
        var value = manager.getCompasSclFileType(null);

        assertFalse(value.isPresent());
    }

    @Test
    void getCompasSclFileType_WhenCalledWithSclType_ThenSclTypeReturned() {
        var scl = readSCL("scl_with_compas_private.scd");
        var compasPrivate = manager.getCompasPrivate(scl);

        compasPrivate.ifPresentOrElse(tPrivate -> {
            var sclFileType = manager.getCompasSclFileType(tPrivate);

            assertTrue(sclFileType.isPresent());
            assertEquals(TSclFileType.CID, sclFileType.get());
        }, () -> fail(COMPAS_PRIVATE_NOT_FOUND));
    }

    @Test
    void getCompasSclFileType_WhenCalledWithoutType_ThenNoTypeReturned() {
        var scl = readSCL("scl_without_filetype_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var sclFileType = manager.getCompasSclFileType(tPrivate);

            assertFalse(sclFileType.isPresent());
        }, () -> fail(COMPAS_PRIVATE_NOT_FOUND));
    }

    @Test
    void createCompasPrivate_WhenCalled_ThenNewCompasPrivateReturned() {
        var compasPrivate = manager.createCompasPrivate();

        assertNotNull(compasPrivate);
        assertEquals(COMPAS_SCL_EXTENSION_TYPE, compasPrivate.getType());
    }
}