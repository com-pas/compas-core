// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SclFileTypeTest extends AbstractEnumTest {
    @Override
    protected Class<? extends Enum<?>> getEnumClass() {
        return SclFileType.class;
    }

    @Test
    void getDescription_WhenCalledForEveryType_ThenEveryTypeHasADescription() {
        for (var type : SclFileType.values()) {
            assertNotNull(type.getDescription());
        }
    }
}