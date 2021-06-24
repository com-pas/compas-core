// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.common;

public enum CompasExtensionsField {
    SCL_NAME_EXTENSION("SclName"),
    SCL_FILETYPE_EXTENSION("SclFileType");

    private final String fieldName;

    CompasExtensionsField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
