// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.commons;

/**
 * The CoMPAS Extension fields that exists. The fieldName is the name how the element is used in the XML.
 */
public enum CompasExtensionsField {
    /**
     * The name of the SCL XML File, will be used in the search and when SCL XML File is saved to the filesystem.
     */
    SCL_NAME_EXTENSION("SclName"),
    /**
     * The type of SCL XML File it is, like IID, SCD and more. TSclFileType indicates which values are allowed.
     */
    SCL_FILETYPE_EXTENSION("SclFileType");

    private final String fieldName;

    CompasExtensionsField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
