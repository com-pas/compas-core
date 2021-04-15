package org.lfenergy.compas.config;

import org.junit.jupiter.api.Test;
import org.lfenergy.compas.commons.CommonConstants;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class XSDFilePropertiesTest {

    @Test
    void testInit() {

        XSDFileProperties xsdFileProperties = new XSDFileProperties();
        xsdFileProperties.init(); // paths null
        assertFalse(xsdFileProperties.getPaths().isEmpty());
        assertTrue(xsdFileProperties.getPaths().containsKey(CommonConstants.XML_DEFAULT_NS_PREFIX));
        assertEquals(CommonConstants.XML_DEFAULT_XSD_PATH,
                xsdFileProperties.getPaths().get(CommonConstants.XML_DEFAULT_NS_PREFIX));

        xsdFileProperties.setPaths(new HashMap<>());
        xsdFileProperties.init(); // path empty
        assertFalse(xsdFileProperties.getPaths().isEmpty());
        assertTrue(xsdFileProperties.getPaths().containsKey(CommonConstants.XML_DEFAULT_NS_PREFIX));
        assertEquals(CommonConstants.XML_DEFAULT_XSD_PATH,
                xsdFileProperties.getPaths().get(CommonConstants.XML_DEFAULT_NS_PREFIX));
    }
}