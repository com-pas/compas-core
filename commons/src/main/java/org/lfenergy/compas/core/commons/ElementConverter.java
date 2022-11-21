// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.CONVERT_TO_ELEMENT_ERROR;
import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.CONVERT_TO_STRING_ERROR;

public class ElementConverter {
    private static final Logger LOGGER = LogManager.getLogger(ElementConverter.class);

    public String convertToString(Element element) {
        return convertToString(element, true);
    }

    public String convertToString(Element element, boolean omitXmlDeclaration) {
        try {
            var buffer = new StringWriter();
            var factory = TransformerFactory.newInstance();
            // Prohibit the use of all protocols by external entities:
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            var transformer = factory.newTransformer();
            if (omitXmlDeclaration) {
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            }

            transformer.transform(new DOMSource(element), new StreamResult(buffer));
            return buffer.toString();
        } catch (TransformerException exp) {
            LOGGER.error("Converting problem: {}", exp.getLocalizedMessage(), exp);
            throw new CompasException(CONVERT_TO_STRING_ERROR, "Error converting to a String!");
        }
    }

    public Element convertToElement(String xml, String rootElementName, String rootElementNS) {
        return convertToElement(new InputSource(new StringReader(xml)), rootElementName, rootElementNS);
    }

    public Element convertToElement(InputStream xml, String rootElementName, String rootElementNS) {
        return convertToElement(new InputSource(xml), rootElementName, rootElementNS);
    }

    private Element convertToElement(InputSource inputSource, String rootElementName, String rootElementNS) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setIgnoringElementContentWhitespace(true);
            // Prohibit the use of all protocols by external entities:
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            // Create DocumentBuilder with default configuration
            var builder = factory.newDocumentBuilder();

            // Parse the content to Document object
            var doc = builder.parse(inputSource);
            return (Element) doc.getElementsByTagNameNS(rootElementNS, rootElementName).item(0);
        } catch (ParserConfigurationException | SAXException | IOException exp) {
            LOGGER.error("Converting problem: {}", exp.getLocalizedMessage(), exp);
            throw new CompasException(CONVERT_TO_ELEMENT_ERROR, "Error converting to a Element!");
        }
    }
}
