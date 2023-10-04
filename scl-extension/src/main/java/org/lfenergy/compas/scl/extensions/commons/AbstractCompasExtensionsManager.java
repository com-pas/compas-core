// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.extensions.commons;

import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.Optional;

/**
 * Abstract class that can be used to get CoMPAS Extensions and Fields from certain XML Elements in the SCL XML File.
 */
public abstract class AbstractCompasExtensionsManager {
    /**
     * Retrieve the value from one of the CoMPAS Extension Fields.
     *
     * @param content The list of private elements in the SCL XML File.
     * @param field   The CoMPAS Extension Field to search for in the list.
     * @param clazz   The class to which the value is casted.
     * @param <T>     The type of value the field holds.
     * @return A Optional containing the value of the field or a empty value if the field can't be found or casted.
     */
    protected <T> Optional<T> getCompasValue(List<Object> content, CompasExtensionsField field, Class<T> clazz) {
        if (content != null) {
            return getCompasElement(content, field)
                    .stream()
                    .map(JAXBElement::getValue)
                    .filter(clazz::isInstance)
                    .map(clazz::cast)
                    .findFirst();
        }
        return Optional.empty();
    }

    /**
     * Retrieve the element from one of the CoMPAS Extension Fields.
     *
     * @param content The list of private elements in the SCL XML File.
     * @param field   The CoMPAS Extension Field to search for in the list.
     * @return A Optional containing the field or is empty if field can't be found in the list.
     */
    @SuppressWarnings("rawtypes")
    protected Optional<JAXBElement> getCompasElement(List<Object> content, CompasExtensionsField field) {
        if (content != null) {
            return content.stream()
                    .filter(JAXBElement.class::isInstance)
                    .map(JAXBElement.class::cast)
                    .filter(element -> element.getName().equals(new QName(CompasExtensionsConstants.COMPAS_EXTENSION_NS_URI, field.getFieldName())))
                    .findFirst();
        }
        return Optional.empty();
    }
}
