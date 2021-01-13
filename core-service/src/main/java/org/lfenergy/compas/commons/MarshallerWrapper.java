// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.commons;

import org.lfenergy.compas.scl.SCL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;


@Component
public class MarshallerWrapper {

    @Autowired
    private Jaxb2Marshaller marshaller;


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
}
