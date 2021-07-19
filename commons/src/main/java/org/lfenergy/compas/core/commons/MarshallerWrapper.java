// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.lfenergy.compas.core.commons.exception.CompasException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.lfenergy.compas.core.commons.exception.CompasErrorCode.*;

public abstract class MarshallerWrapper<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarshallerWrapper.class);

    private final Unmarshaller jaxbUnmarshaller;
    private final Marshaller jaxbMarshaller;

    protected MarshallerWrapper(Unmarshaller jaxbUnmarshaller, Marshaller jaxbMarshaller) {
        this.jaxbUnmarshaller = jaxbUnmarshaller;
        this.jaxbMarshaller = jaxbMarshaller;
    }

    protected abstract Class<T> getResultClass();

    public String marshall(final T obj) {
        try {
            StringWriter sw = new StringWriter();
            Result result = new StreamResult(sw);
            jaxbMarshaller.marshal(obj, result);

            return sw.toString();
        } catch (JAXBException exp) {
            String message = "Error marshalling the Class.";
            LOGGER.error(message, exp);
            throw new CompasException(MARSHAL_ERROR_CODE, message);
        }
    }

    public T unmarshall(final byte[] xml) {
        ByteArrayInputStream input = new ByteArrayInputStream(xml);
        return unmarshall(input);
    }

    public T unmarshall(final InputStream xml) {
        try {
            return cast(jaxbUnmarshaller.unmarshal(new StreamSource(xml)));
        } catch (JAXBException exp) {
            String message = "Error unmarshalling to the Class.";
            LOGGER.error(message, exp);
            throw new CompasException(UNMARSHAL_ERROR_CODE, message);
        }
    }

    @SuppressWarnings("unchecked")
    private T cast(Object result) {
        if (!result.getClass().isAssignableFrom(getResultClass())) {
            throw new CompasException(UNMARSHAL_ERROR_CODE, "Error unmarshalling to the Class. Invalid class");
        }
        return (T) result;
    }

    public abstract static class Builder<W extends MarshallerWrapper<T>, T> {
        private static final String COMPAS_SCL_SCHEMAS_JSONPATH = "/compas/scl/schemas";
        private static final String CONTEXT_PATH_PROP = "contextPath";
        private static final String XSD_PATH_PROP = "xsdPath";
        private static final String NAMESPACE_PROP = "namespace";

        // Path to the YAML File containing the schema properties.
        private String yamlFilePath;

        protected abstract W createMarshallerWrapper(Unmarshaller jaxbUnmarshaller,
                                                     Marshaller jaxbMarshaller);

        public Builder<W, T> withProperties(String yamlFilePath) {
            this.yamlFilePath = yamlFilePath;
            return this;
        }

        private List<SchemaConfig> getSchemaConfigs() {
            if (yamlFilePath == null || yamlFilePath.isBlank()) {
                throw new CompasException(CONFIGURATION_ERROR_CODE,
                        "No configuration file configured (yamlFilePath)");
            }

            var schemaConfigs = new ArrayList<SchemaConfig>();
            try {
                // Search for the YAml File on the classpath.
                var inputStream = Resources.getResource(yamlFilePath)
                        .orElseThrow(() -> new CompasException(RESOURCE_NOT_FOUND_ERROR_CODE,
                                String.format("Resource %s not found", yamlFilePath)));
                var objectMapper = new ObjectMapper(new YAMLFactory());
                var jsonNode = objectMapper.readTree(inputStream);

                ;
                var pathsNode = jsonNode.at(COMPAS_SCL_SCHEMAS_JSONPATH);
                if (pathsNode != null && pathsNode.getNodeType() == JsonNodeType.ARRAY) {
                    Iterable<JsonNode> nodes = pathsNode::elements;
                    // Walk through the schemas and check if all needed parameters are filled.
                    nodes.forEach(node -> {
                        if (node.has(XSD_PATH_PROP) && node.has(NAMESPACE_PROP) && node.has(CONTEXT_PATH_PROP)) {
                            schemaConfigs.add(
                                    new SchemaConfig(node.get(XSD_PATH_PROP).textValue(),
                                            node.get(NAMESPACE_PROP).textValue(),
                                            node.get(CONTEXT_PATH_PROP).textValue()));
                        } else {
                            throw new CompasException(PROPERTY_ERROR_ERROR_CODE,
                                    String.format("One of the properties (%s, %s, %s) has no value",
                                            XSD_PATH_PROP, NAMESPACE_PROP, CONTEXT_PATH_PROP));
                        }
                    });
                } else {
                    throw new CompasException(CONFIGURATION_ERROR_CODE,
                            String.format("Configuration for marshaller (%s) didn't contain the path %s",
                                    yamlFilePath, COMPAS_SCL_SCHEMAS_JSONPATH));
                }
            } catch (IOException exp) {
                var message = "I/O Error reading YAML File";
                LOGGER.error(message, exp);
                throw new CompasException(INVALID_YML_ERROR_CODE, message);
            }

            return schemaConfigs;
        }

        private List<String> getContextPaths(List<SchemaConfig> schemaConfigs) {
            // Convert the SchemaConfig List to a List containing only the ContextPaths.
            return schemaConfigs.stream()
                    .map(SchemaConfig::getContextPath)
                    .collect(Collectors.toList());
        }

        private String getImportStatements(List<SchemaConfig> schemaConfigs) {
            // Convert the SchemaConfig List to a String containing the Import Statements sued to combine multiple
            // XSD Schemas.
            return schemaConfigs.stream()
                    .map(schemaConfig ->
                            "<xs:import namespace=\"" + schemaConfig.getNamespace() + "\" schemaLocation=\""
                                    + schemaConfig.getXsdPath().toString() + "\"/>\n")
                    .collect(Collectors.joining());
        }

        public W build() {
            try {
                // Create the JAXB Context with the configured context paths. The list is separated by a colon
                // as needed by the JAxbContext.
                var schemaConfigs = getSchemaConfigs();
                var contextPaths = String.join(":", getContextPaths(schemaConfigs));

                var jaxbContext = JAXBContext.newInstance(contextPaths);
                var jaxbMarshaller = jaxbContext.createMarshaller();
                var jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                // Setup schema validator
                var factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                // To make load of all different type of schemas and also make imports/includes in these schemas
                // work the solution is to make a combined XSD Schema containing all other schemas as import.
                // Import is used, because the schemas can have different namespaces.
                var combinedXsdSchema =
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                                + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" elementFormDefault=\"qualified\">\n"
                                + getImportStatements(schemaConfigs)
                                + "</xs:schema>";
                var schema = factory.newSchema(new StreamSource(new StringReader(combinedXsdSchema), "topSchema"));
                jaxbMarshaller.setSchema(schema);
                jaxbUnmarshaller.setSchema(schema);

                return createMarshallerWrapper(jaxbUnmarshaller, jaxbMarshaller);
            } catch (JAXBException | SAXException exp) {
                var message = "Error creating JAXB Marshaller and Unmarshaller.";
                LOGGER.error(message, exp);
                throw new CompasException(CREATION_ERROR_CODE, message);
            }
        }
    }

    /**
     * POJO Class to hold all the schema configuration, so that we only need to walk through the JSON Content once.
     */
    private static class SchemaConfig {
        private final URL xsdPath;
        private final String namespace;
        private final String contextPath;

        public SchemaConfig(String xsdPath, String namespace, String contextPath) {
            // Convert the XSD Path to a URL, that will be used in the import statement.
            // If the XSD Path is not correct loading will fail.
            this.xsdPath = Resources.getResource(xsdPath)
                    .orElseThrow(() -> new CompasException(RESOURCE_NOT_FOUND_ERROR_CODE,
                            String.format("Resource %s not found", xsdPath)));
            this.namespace = namespace;
            this.contextPath = contextPath;
        }

        public URL getXsdPath() {
            return xsdPath;
        }

        public String getNamespace() {
            return namespace;
        }

        public String getContextPath() {
            return contextPath;
        }
    }
}
