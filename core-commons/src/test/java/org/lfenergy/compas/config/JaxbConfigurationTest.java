package org.lfenergy.compas.config;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
class JaxbConfigurationTest {

    private JaxbConfiguration jaxbConfiguration;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void setUp(){
        jaxbConfiguration = new JaxbConfiguration();
    }

    @Test
    void testShouldReturnOKWhenCallJaxb2MarshallerFactory() throws Exception {
        Jaxb2Marshaller res = jaxbConfiguration.jaxb2Marshaller(resourceLoader,null);
        assertNotNull(res);
    }
}