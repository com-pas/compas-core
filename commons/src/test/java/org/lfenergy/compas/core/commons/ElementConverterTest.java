// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.CONVERT_TO_ELEMENT_ERROR;

class ElementConverterTest {
    private static final String SIMPLE_SCL_NS = "http://www.iec.ch/61850/2003/SCL";
    private static final String SIMPLE_SCL_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<SCL xmlns=\"" + SIMPLE_SCL_NS + "\" version=\"2007\" revision=\"B\" release=\"4\">" +
                    "</SCL>";

    private final ElementConverter converter = new ElementConverter();

    @Test
    void convertToString_WhenCalledWithXMLElementWithoutDeclaration_ThenElementConvertedToAString() throws ParserConfigurationException {
        var element = createSimpleElement();

        var result = converter.convertToString(element);

        assertEquals("<root primary=\"true\"/>", result);
    }

    @Test
    void convertToString_WhenCalledWithXMLElementWithDeclaration_ThenElementConvertedToAStringWithDeclaration() throws ParserConfigurationException {
        var element = createSimpleElement();

        var result = converter.convertToString(element, false);

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<root primary=\"true\"/>", result);
    }

    @Test
    void convertToElement_WhenCalledWithSclXmlAsString_ThenConvertedToXMLElement() {
        var element = converter.convertToElement(SIMPLE_SCL_XML, "SCL", SIMPLE_SCL_NS);

        assertNotNull(element);
        assertEquals("SCL", element.getLocalName());
        assertTrue(element.hasAttribute("version"));
        assertEquals("2007", element.getAttribute("version"));
    }

    @Test
    void convertToElement_WhenCalledWithSclXmlAsStream_ThenConvertedToXMLElement() {
        var element = converter.convertToElement(
                new ByteArrayInputStream(SIMPLE_SCL_XML.getBytes(StandardCharsets.UTF_8))
                , "SCL", SIMPLE_SCL_NS);

        assertNotNull(element);
        assertEquals("SCL", element.getLocalName());
        assertTrue(element.hasAttribute("version"));
        assertEquals("2007", element.getAttribute("version"));
    }

    @Test
    void convertToElement_WhenCalledWithUnknownRootElement_ThenNullReturnedBecauseNoRootElement() {
        var randomXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<root primary=\"true\"/>";

        var element = converter.convertToElement(randomXml, "SCL", SIMPLE_SCL_NS);

        assertNull(element);
    }

    @Test
    void convertToElement_WhenCalledWithInvalidXML_ThenExceptionThrown() {
        var invalidXml = "<root></invalid>";

        var exception = assertThrows(CompasException.class, () -> {
            converter.convertToElement(invalidXml, "SCL", SIMPLE_SCL_NS);
        });
        assertEquals(CONVERT_TO_ELEMENT_ERROR, exception.getErrorCode());
    }

    private Element createSimpleElement() throws ParserConfigurationException {
        var dbf = DocumentBuilderFactory.newInstance();
        var builder = dbf.newDocumentBuilder();
        var doc = builder.newDocument();

        // create the root element node
        var element = doc.createElement("root");
        element.setAttribute("primary", "true");
        doc.appendChild(element);

        return element;
    }
}
