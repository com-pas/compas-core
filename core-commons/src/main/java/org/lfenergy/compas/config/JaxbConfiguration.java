// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.config;

import org.lfenergy.compas.scl.SCL;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(XSDFileProperties.class)
public class JaxbConfiguration {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(ResourceLoader resourceLoader,XSDFileProperties xsdFileProperties) throws Exception {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setMarshallerProperties(Collections.singletonMap(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
        jaxb2Marshaller.setClassesToBeBound(SCL.class);
        List<Resource> resources = new ArrayList<>();
        if(xsdFileProperties != null) {

            for (Map.Entry<String, String> path : xsdFileProperties.getPaths().entrySet()) {
                Resource resource = resourceLoader.getResource(path.getValue());
                if(resource.exists())
                    resources.add(resource);
            }

        } else {
            Resource resource = resourceLoader.getResource("classpath:schema/SCL.xsd");
            if(resource.exists())
                resources.add(resource);
        }

        if(!resources.isEmpty()) {
            jaxb2Marshaller.setSchemas(resources.toArray(Resource[]::new));
        }
        jaxb2Marshaller.afterPropertiesSet();

        return jaxb2Marshaller;
    }
}
