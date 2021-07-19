// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.commons.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
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
