// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.commons;

/**
 * The CoMPAS Extension fields that exists. The fieldName is the name how the element is used in the XML.
 * For a description of the fields, see the XSD.
 */
public enum CompasExtensionsField {
    SCL_NAME("SclName"),
    SCL_FILE_TYPE("SclFileType"),
    LABELS("Labels"),
    FLOW("Flow"),
    CONNECTIVITY_NODE("ConnectivityNode"),
    BAY("Bay"),
    L_DEVICE("LDevice"),
    CRITERIA("Criteria"),
    ICD_HEADER("ICDHeader"),
    SYSTEM_VERSION("SystemVersion"),
    FUNCTION("Function");

    private final String fieldName;

    CompasExtensionsField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
