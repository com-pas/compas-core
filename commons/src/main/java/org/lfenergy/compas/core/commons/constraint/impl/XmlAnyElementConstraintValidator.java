// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.constraint.impl;

import org.lfenergy.compas.core.commons.constraint.XmlAnyElementValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * Validator to execute the check on fields annotated with {@link XmlAnyElementValid} to check if the number of
 * element are correct and also if the element(s) in the list have the correct Local Name and the correct Namespace.
 */
public class XmlAnyElementConstraintValidator implements ConstraintValidator<XmlAnyElementValid, List<Element>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlAnyElementConstraintValidator.class);

    private String elementName;
    private String elementNamespace;

    @Override
    public void initialize(XmlAnyElementValid constraintAnnotation) {
        LOGGER.debug("Initializing XmlAnyElementValid constraint for List of Elements");
        this.elementName = constraintAnnotation.elementName();
        this.elementNamespace = constraintAnnotation.elementNamespace();
    }

    @Override
    public boolean isValid(List<Element> elements, ConstraintValidatorContext context) {
        // Check if there are elements in the List that don't match the name or namespace.
        var numberOfIncorrectElements =
                elements.stream()
                        .filter(element ->
                                !elementName.equals(element.getLocalName())
                                        || !elementNamespace.equals(element.getNamespaceURI()))
                        .count();
        return numberOfIncorrectElements == 0;
    }
}
