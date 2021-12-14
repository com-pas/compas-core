// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2003.commons;

import org.lfenergy.compas.scl.extensions.commons.AbstractCompasExtensionsManager;
import org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField;
import org.lfenergy.compas.scl.extensions.model.SclFileType;
import org.lfenergy.compas.scl2003.model.SCL;
import org.lfenergy.compas.scl2003.model.TPrivate;

import javax.xml.bind.JAXBElement;
import java.util.Optional;

import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsConstants.COMPAS_SCL_EXTENSION_TYPE;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_FILETYPE_EXTENSION;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_NAME_EXTENSION;

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

    public Optional<String> getCompasSclName(TPrivate compasPrivate) {
        return getCompasValue(compasPrivate, SCL_NAME_EXTENSION, String.class);
    }

    public Optional<SclFileType> getCompasSclFileType(TPrivate compasPrivate) {
        return getCompasValue(compasPrivate, SCL_FILETYPE_EXTENSION, SclFileType.class);
    }

    private <T> Optional<T> getCompasValue(TPrivate compasPrivate, CompasExtensionsField field, Class<T> clazz) {
        if (compasPrivate != null) {
            return getCompasValue(compasPrivate.getContent(), field, clazz);
        }
        return Optional.empty();
    }

    @SuppressWarnings("rawtypes")
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
        tPrivate.setType(COMPAS_SCL_EXTENSION_TYPE);
        return tPrivate;
    }
}
