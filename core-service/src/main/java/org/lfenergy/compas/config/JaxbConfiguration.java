// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.config;

import org.lfenergy.compas.scl.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(XSDFileProperties.class)
public class JaxbConfiguration {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private XSDFileProperties xsdFileProperties;

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller(){
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(SCL.class);
        List<Resource> resources = new ArrayList<>();
        if(xsdFileProperties != null) {

            for (Map.Entry<String, String> path : xsdFileProperties.getPaths().entrySet()) {
                String filePath = path.getValue();
                resources.add(resourceLoader.getResource(filePath));
            }

        } else {
            Resource resource = resourceLoader.getResource("classpath:schema/SCL.xsd");
            if(resource.exists())
                resources.add(resource);
        }

        if(!resources.isEmpty())
            jaxb2Marshaller.setSchemas(resources.toArray(Resource[]::new));

        return jaxb2Marshaller;
    }
}
