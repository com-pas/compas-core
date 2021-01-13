// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.commons;

public class CommonConstants {

    private CommonConstants() {
        throw new IllegalStateException("CommonConstants class");
    }

    public static final String XML_DEFAULT_NS_PREFIX = "scl";
    public static final String XML_DEFAULT_NS_URI = "http://www.iec.ch/61850/2003/SCL";
    public static final String XML_DEFAULT_XSD_PATH = "classpath:schema/SCL.xsd";
}
