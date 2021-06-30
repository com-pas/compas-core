// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.scl.commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.NoArgsConstructor;
import org.lfenergy.compas.scl.model.SCL;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.*;


public class MarshallerWrapper {

    private static final String PREFIX = "compas.scl.schema.paths";
    private Jaxb2Marshaller marshaller;

    private MarshallerWrapper(){}

    public String marshall(final SCL obj){
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        marshaller.marshal(obj, result);

        return sw.toString();
    }

    public SCL unmarshall(final InputStream xml){
        return (SCL) marshaller.unmarshal(new StreamSource(xml));
    }

    public SCL unmarshall(final byte[] xml) {
        ByteArrayInputStream input = new ByteArrayInputStream( xml);
        return unmarshall(input);
    }

    @NoArgsConstructor
    public static class Builder {
        private String propPath;
        private Map<String, String> schemaMap = new HashMap<>();

        public Builder withProperties(String propPath){
            this.propPath = propPath;
            this.schemaMap.clear();
            return this;
        }

        public Builder withSchemaMap(Map<String, String> schemaMap){
            this.schemaMap = schemaMap;
            propPath = null;
            return this;
        }

        public Jaxb2Marshaller jaxb2Marshaller(@NonNull Map<String, String> nsPathMap) throws Exception {
            Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            jaxb2Marshaller.setMarshallerProperties(Collections.singletonMap(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE));
            jaxb2Marshaller.setContextPaths("org.lfenergy.compas.scl.extensions.model", "org.lfenergy.compas.scl.model");
            List<Resource> resources = new ArrayList<>();
            if(!nsPathMap.isEmpty()) {
                nsPathMap.forEach((k, v) -> {
                    Resource resource = resourceLoader.getResource(v);
                    if(resource.exists())
                        resources.add(resource);
                });
            } else {
                Resource resource = resourceLoader.getResource(CommonConstants.XML_DEFAULT_XSD_PATH);
                if(resource.exists())
                    resources.add(resource);
            }

            if(!resources.isEmpty()) {
                jaxb2Marshaller.setSchemas(resources.toArray(Resource[]::new));
            }
            jaxb2Marshaller.afterPropertiesSet();

            return jaxb2Marshaller;
        }

        protected void fillNsPathMap() throws IOException {
            if(!schemaMap.isEmpty()) return;
            if(propPath == null || propPath.trim().isEmpty()){
                schemaMap.put("scl","classpath:schema/SCL.xsd");
                schemaMap.put("compas","classpath:xsd/SCL_CoMPAS.xsd");
            } else {

                DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
                Resource propResource = defaultResourceLoader.getResource(propPath);
                if (propResource.exists()) {
                    if (propPath.endsWith(".yml") || propPath.endsWith(".yaml")) {
                        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
                        JsonNode jsonNode = objectMapper.readTree(propResource.getFile());
                        schemaMap = getXSDProperties(jsonNode);

                    } else if (propPath.endsWith(".properties")) {
                        Properties properties = new Properties();
                        try (InputStream input = new FileInputStream(propResource.getFile())) {
                            properties.load(input);
                            schemaMap = getXSDProperties(properties);
                        }
                    }
                }
            }
        }

        public MarshallerWrapper build() throws Exception {
            if(schemaMap.isEmpty()) {
                fillNsPathMap();
            }
            MarshallerWrapper marshallerWrapper = new MarshallerWrapper();
            marshallerWrapper.marshaller = jaxb2Marshaller(schemaMap);

            return marshallerWrapper;
        }

        protected Map<String, String> getXSDProperties(Properties properties){
            Iterator<Object> it = properties.keys().asIterator();
            while(it.hasNext()){
                String key = (String) it.next();
                if(!key.contains(PREFIX)) continue;

                String prefix = key.substring(PREFIX.length() + 1);
                schemaMap.put(prefix,properties.getProperty(key));
            }

            return schemaMap;
        }
        protected Map<String, String> getXSDProperties(JsonNode jsonNode) throws InvalidPropertiesFormatException {
            Map<String, String> nsPathMap = new HashMap<>();
            JsonNode pathsNode = jsonNode.at("/compas/scl/schema/paths");
            if(pathsNode == null || pathsNode.isNull() || pathsNode.getNodeType() != JsonNodeType.ARRAY){
                throw new InvalidPropertiesFormatException("XSD properties are missing!");
            }
            Iterator<JsonNode> it =pathsNode.elements();
            while(it.hasNext()) {
                JsonNode node = it.next();
                Iterator<String> fieldIt = node.fieldNames();
                while(fieldIt.hasNext()) {
                    String prefix = fieldIt.next();
                    nsPathMap.put(prefix, node.get(prefix).textValue());
                }
            }
            return nsPathMap;
        }
    }
}
