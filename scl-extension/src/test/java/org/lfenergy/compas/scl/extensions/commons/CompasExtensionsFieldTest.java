// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_FILE_TYPE;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_NAME;

class CompasExtensionsFieldTest {
    @Test
    void enum_WhenRetrievingTheFieldName_ThenTheCorrectStringIsReturned() {
        assertEquals("SclName", SCL_NAME.getFieldName());
        assertEquals("SclFileType", SCL_FILE_TYPE.getFieldName());
    }
}
