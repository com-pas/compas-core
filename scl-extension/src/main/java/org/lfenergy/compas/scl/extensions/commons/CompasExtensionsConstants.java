// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.commons;

public class CompasExtensionsConstants {
    CompasExtensionsConstants() {
        throw new UnsupportedOperationException("CompasExtensionsConstants class");
    }

    public static final String XML_DEFAULT_NS_PREFIX = "compas";
    public static final String COMPAS_EXTENSION_NS_URI = "https://www.lfenergy.org/compas/v1";
    public static final String COMPAS_SCL_EXTENSION_TYPE = "compas_scl";
    public static final String XML_DEFAULT_XSD_PATH = "classpath:xsd/SCL_CoMPAS.xsd";
    public static final String JAXB_CONTEXT_PATH = "org.lfenergy.compas.scl2007b4.model";
}
