// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.scl.extensions.model.TSclFileType;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsConstants.COMPAS_SCL_EXTENSION_TYPE;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_FILETYPE_EXTENSION;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_NAME_EXTENSION;
import static org.lfenergy.compas.scl.util.ReadTestFile.readSCL;

class CompasExtensionsManagerTest {
    private static final String COMPAS_PRIVATE_NOTFOUND = "No Compas Private Element found";

    private CompasExtensionsManager manager = new CompasExtensionsManager();

    @Test
    void getCompasPrivate_WhenCalledWithPrivate_ThenCompasPrivateReturned() throws Exception {
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
    void getCompasElement_WhenCalledWithoutSclName_ThenNoSclNameReturned() throws Exception {
        var scl = readSCL("scl_without_sclname_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var compasElement = manager.getCompasElement(tPrivate, SCL_NAME_EXTENSION);

            assertFalse(compasElement.isPresent());
        }, () -> fail(COMPAS_PRIVATE_NOTFOUND));
    }

    @Test
    void getCompasElement_WhenCalledWithSclName_ThenSclNameReturned() throws Exception {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var compasElement = manager.getCompasElement(tPrivate, SCL_NAME_EXTENSION);

            assertTrue(compasElement.isPresent());
            assertEquals("project", compasElement.get().getValue().toString());
        }, () -> fail(COMPAS_PRIVATE_NOTFOUND));
    }

    @Test
    void getCompasElement_WhenCalledWithoutFileType_ThenNoFileTypeReturned() throws Exception {
        var scl = readSCL("scl_without_filetype_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var compasElement = manager.getCompasElement(tPrivate, SCL_FILETYPE_EXTENSION);

            assertFalse(compasElement.isPresent());
        }, () -> fail(COMPAS_PRIVATE_NOTFOUND));
    }

    @Test
    void getCompasElement_WhenCalledWithFileType_ThenFileTypeReturned() throws Exception {
        var scl = readSCL("scl_with_compas_private.scd");

        var compasPrivate = manager.getCompasPrivate(scl);
        compasPrivate.ifPresentOrElse(tPrivate -> {
            var compasElement = manager.getCompasElement(tPrivate, SCL_FILETYPE_EXTENSION);

            assertTrue(compasElement.isPresent());
            assertEquals(TSclFileType.CID, compasElement.get().getValue());
        }, () -> fail(COMPAS_PRIVATE_NOTFOUND));
    }

    @Test
    void getCompasSclName_WhenCalledWithsclName_TheSclNameReturned() throws Exception {
        var scl = readSCL("scl_with_compas_private.scd");
        var compasPrivate = manager.getCompasPrivate(scl);

        compasPrivate.ifPresentOrElse(tPrivate -> {
            var sclName = manager.getCompasSclName(compasPrivate.get());

            assertTrue(sclName.isPresent());
            assertEquals("project", sclName.get());
        }, () -> fail(COMPAS_PRIVATE_NOTFOUND));
    }

    @Test
    void getCompasSclName_WhenCalledWithNosclName_TheEmptyOptionalReturned() throws Exception {
        var scl = readSCL("scl_without_sclname_compas_private.scd");
        var compasPrivate = manager.getCompasPrivate(scl);

        compasPrivate.ifPresentOrElse(tPrivate -> {
            var sclName = manager.getCompasSclName(compasPrivate.get());

            assertFalse(sclName.isPresent());
        }, () -> fail(COMPAS_PRIVATE_NOTFOUND));
    }

    @Test
    void createCompasPrivate_WhenCalled_ThenNewCompasPrivateReturned() {
        var compasPrivate = manager.createCompasPrivate();

        assertNotNull(compasPrivate);
        assertEquals(COMPAS_SCL_EXTENSION_TYPE, compasPrivate.getType());
    }
}