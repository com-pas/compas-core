// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.commons;

public class SclConstants {
    SclConstants() {
        throw new UnsupportedOperationException("CommonConstants class");
    }

    public static final String XML_DEFAULT_NS_PREFIX = "scl";
    public static final String SCL_NS_URI = "http://www.iec.ch/61850/2003/SCL";
    public static final String XML_DEFAULT_XSD_PATH = "classpath:xsd/SCL.xsd";
    public static final String JAXB_CONTEXT_PATH = "org.lfenergy.compas.scl.extensions.model";
}
