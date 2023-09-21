// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.model;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
    private final static QName _XmlElementBPojo_QNAME = new QName("https://www.lfenergy.org/compas/example", "XmlElementBPojo");

    public XmlElementAPojo createXmlElementAPojo() {
        return new XmlElementAPojo();
    }

    @XmlElementDecl(namespace = "https://www.lfenergy.org/compas/example", name = "XmlElementBPojo")
    public JAXBElement<XmlElementBPojo> createXmlElementBPojo(XmlElementBPojo value) {
        return new JAXBElement<>(_XmlElementBPojo_QNAME, XmlElementBPojo.class, null, value);
    }

}
