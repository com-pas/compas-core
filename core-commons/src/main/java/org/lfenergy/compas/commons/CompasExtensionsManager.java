// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.commons;

import org.lfenergy.compas.scl.SCL;
import org.lfenergy.compas.scl.TPrivate;
import org.lfenergy.compas.scl.extensions.common.AbstractCompasExtensionsManager;
import org.lfenergy.compas.scl.extensions.common.CompasExtensionsField;

import javax.xml.bind.JAXBElement;
import java.util.Optional;

import static org.lfenergy.compas.scl.extensions.common.CompasExtensionsConstants.COMPAS_SCL_EXTENSION_TYPE;
import static org.lfenergy.compas.scl.extensions.common.CompasExtensionsField.SCL_NAME_EXTENSION;

public class CompasExtensionsManager extends AbstractCompasExtensionsManager {

    public Optional<TPrivate> getCompasPrivate(SCL scl) {
        if (scl != null) {
            return scl.getPrivate()
                    .stream()
                    .filter(tPrivate -> tPrivate.getType().equals(COMPAS_SCL_EXTENSION_TYPE))
                    .findFirst();
        }
        return Optional.empty();
    }

    public Optional<String> getCompasName(TPrivate compasPrivate) {
        var compasElement = getCompasElement(compasPrivate, SCL_NAME_EXTENSION);
        return compasElement
                .stream()
                .map(JAXBElement::getValue)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .findFirst();
    }

    public Optional<JAXBElement> getCompasElement(TPrivate compasPrivate, CompasExtensionsField field) {
        if (compasPrivate != null) {
            return getCompasElement(compasPrivate.getContent(), field);
        }
        return Optional.empty();
    }

    public TPrivate createCompasPrivate() {
        // Creating a private
        var tPrivate = new TPrivate();
        // Setting the type (required for a SCL private element)
        tPrivate.setType("compas_scl");
        return tPrivate;
    }
}
