// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.constraint.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.constraint.XmlAnyElementValid;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlAnyElementConstraintValidatorTest {
    private static final String ELEMENT_NAME = "valid";
    private static final String ELEMENT_NS = "https://valid.org";

    private Document document;
    private Validator validator;

    @BeforeEach
    void setupValidator() throws ParserConfigurationException {
        var documentFactory = DocumentBuilderFactory.newInstance();
        var documentBuilder = documentFactory.newDocumentBuilder();
        document = documentBuilder.newDocument();

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void isValid_WhenCalledWithCorrectElement_ThenNoViolations() {
        var simplePojo = new SimplePojo();
        simplePojo.getElements().add(document.createElementNS(ELEMENT_NS, ELEMENT_NAME));

        var violations = validator.validate(simplePojo);
        assertTrue(violations.isEmpty());
    }

    @Test
    void isValid_WhenCalledWithIncorrectElement_ThenViolationFound() {
        var simplePojo = new SimplePojo();
        simplePojo.getElements().add(document.createElementNS("https://OtherNS.org", "Other"));

        var violations = validator.validate(simplePojo);
        assertEquals(1, violations.size());
    }

    @Test
    void isValid_WhenCalledWithMultipleElementAndOneIncorrect_ThenViolationFound() {
        var simplePojo = new SimplePojo();
        simplePojo.getElements().add(document.createElementNS("https://OtherNS.org", "Other"));
        simplePojo.getElements().add(document.createElementNS(ELEMENT_NS, ELEMENT_NAME));
        simplePojo.getElements().add(document.createElementNS(ELEMENT_NS, ELEMENT_NAME));

        var violations = validator.validate(simplePojo);
        assertEquals(1, violations.size());
    }

    @Test
    void isValid_WhenCalledWithMultipleElementAndOneIncorrectNS_ThenViolationFound() {
        var simplePojo = new SimplePojo();
        simplePojo.getElements().add(document.createElementNS("https://OtherNS.org", ELEMENT_NAME));
        simplePojo.getElements().add(document.createElementNS(ELEMENT_NS, ELEMENT_NAME));
        simplePojo.getElements().add(document.createElementNS(ELEMENT_NS, ELEMENT_NAME));

        var violations = validator.validate(simplePojo);
        assertEquals(1, violations.size());
    }

    private static final class SimplePojo {
        @XmlAnyElementValid(elementName = ELEMENT_NAME, elementNamespace = ELEMENT_NS)
        private final List<Element> elements = new ArrayList<>();

        public List<Element> getElements() {
            return elements;
        }
    }
}