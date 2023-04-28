// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XmlElementAPojo")
public class XmlElementAPojo {
    @XmlAttribute(name = "id", required = true)
    public String id;
    @XmlElement(name = "Other")
    public XmlElementBPojo other;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public XmlElementBPojo getOther() {
        return other;
    }

    public void setOther(XmlElementBPojo other) {
        this.other = other;
    }
}
