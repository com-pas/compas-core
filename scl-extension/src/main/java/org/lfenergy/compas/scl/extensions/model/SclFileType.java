// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "tSclFileType", namespace = "https://www.lfenergy.org/compas/extension/v1")
@XmlEnum
public enum SclFileType {
    SSD("Substation Specification Description"),
    IID("IED Instance Description"),
    ICD("IED Capability Description"),
    SCD("Substation Configuration Description"),
    CID("Configured IED Description"),
    SED("System Exchange Description"),
    ISD("IED Specification Description"),
    STD("System Template Definition");

    private final String description;

    SclFileType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
