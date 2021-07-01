// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.commons;

import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.scl.extensions.commons.CompasExtensionsField.SCL_NAME_EXTENSION;

class AbstractCompasExtensionsManagerTest {
    private AbstractCompasExtensionsManager manager = new AbstractCompasExtensionsManager() {
    };

    @Test
    void getCompasElement_WhenNullPassedAsList_ThenEmptyOptionalReturned() {
        var result = manager.getCompasElement(null, SCL_NAME_EXTENSION);

        assertFalse(result.isPresent());
    }

    @Test
    void getCompasElement_WhenCalledWithListContainingSclName_ThenSclNameReturned() {
        var elements = createElements(true);
        var result = manager.getCompasElement(elements, SCL_NAME_EXTENSION);

        assertTrue(result.isPresent());
        assertEquals("project", result.get().getValue());
    }

    @Test
    void getCompasElement_WhenCalledWithListNotContainingSclName_ThenEmptyOptionalReturned() {
        var elements = createElements(false);
        var result = manager.getCompasElement(elements, SCL_NAME_EXTENSION);

        assertFalse(result.isPresent());
    }

    private List<Object> createElements(boolean withSclName) {
        var elements = new ArrayList<>();

        if (withSclName) {
            var qname = new QName(CompasExtensionsConstants.COMPAS_EXTENSION_NS_URI, SCL_NAME_EXTENSION.getFieldName());
            var jaxbElement = new JAXBElement<>(qname, String.class, "project");
            elements.add(jaxbElement);
        }

        return elements;
    }
}