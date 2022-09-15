// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.commons;

/**
 * Some standard constants that can be used in code to reference CoMPAS Extensions.
 */
public class CompasExtensionsConstants {
    CompasExtensionsConstants() {
        throw new UnsupportedOperationException("CompasExtensionsConstants class");
    }

    public static final String XML_DEFAULT_NS_PREFIX = "compas";
    public static final String COMPAS_EXTENSION_NS_URI = "https://www.lfenergy.org/compas/extension/v1";
    public static final String COMPAS_SCL_EXTENSION_TYPE = "compas_scl";
    public static final String JAXB_CONTEXT_PATH = "org.lfenergy.compas.scl.extensions.model";
}
