// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2007b.commons;

import org.lfenergy.compas.scl.extensions.commons.AbstractCompasExtensionsManager;
import org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField;
import org.lfenergy.compas.scl.extensions.model.SclFileType;
import org.lfenergy.compas.scl.extensions.model.TCompasLabels;
import org.lfenergy.compas.scl2007b.model.SCL;
import org.lfenergy.compas.scl2007b.model.TPrivate;

import javax.xml.bind.JAXBElement;
import java.util.Optional;

import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsConstants.COMPAS_SCL_EXTENSION_TYPE;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.*;

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

    private <T> Optional<T> getCompasValue(TPrivate compasPrivate, CompasExtensionsField field, Class<T> clazz) {
        if (compasPrivate != null) {
            return getCompasValue(compasPrivate.getContent(), field, clazz);
        }
        return Optional.empty();
    }

    public Optional<SclFileType> getCompasSclFileType(TPrivate compasPrivate) {
        return getCompasValue(compasPrivate, SCL_FILE_TYPE, SclFileType.class);
    }

    public Optional<String> getCompasSclName(TPrivate compasPrivate) {
        return getCompasValue(compasPrivate, SCL_NAME, String.class);
    }

    public Optional<TCompasLabels> getLabels(TPrivate compasPrivate) {
        return getCompasValue(compasPrivate, LABELS, TCompasLabels.class);
    }

    public TPrivate createCompasPrivate() {
        // Creating a private
        var tPrivate = new TPrivate();
        // Setting the type (required for a SCL private element)
        tPrivate.setType(COMPAS_SCL_EXTENSION_TYPE);
        return tPrivate;
    }

    @SuppressWarnings("rawtypes")
    public Optional<JAXBElement> getCompasElement(TPrivate compasPrivate, CompasExtensionsField field) {
        if (compasPrivate != null) {
            return getCompasElement(compasPrivate.getContent(), field);
        }
        return Optional.empty();
    }
}
