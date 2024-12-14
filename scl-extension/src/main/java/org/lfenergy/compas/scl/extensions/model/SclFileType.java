// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.model;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsConstants.COMPAS_EXTENSION_NS_URI;

@XmlType(name = "tCompasSclFileType", namespace = COMPAS_EXTENSION_NS_URI)
@XmlEnum
public enum SclFileType {
    SSD("Substation Specification Description"),
    IID("IED Instance Description"),
    ICD("IED Capability Description"),
    SCD("Substation Configuration Description"),
    CID("Configured IED Description"),
    SED("System Exchange Description"),
    ISD("IED Specification Description"),
    STD("System Template Definition"),
    FSD("Function Specification Description");

    private final String description;

    SclFileType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
