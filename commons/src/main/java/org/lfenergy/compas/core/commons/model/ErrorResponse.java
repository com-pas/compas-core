// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.model;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import static org.lfenergy.compas.core.commons.CommonConstants.COMPAS_COMMONS_V1_NS_URI;

@XmlRootElement(name = "ErrorResponse", namespace = COMPAS_COMMONS_V1_NS_URI)
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {
    @Valid
    @XmlElement(name = "ErrorMessage", namespace = COMPAS_COMMONS_V1_NS_URI, required = true)
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void addErrorMessage(String code, String message) {
        errorMessages.add(new ErrorMessage(code, message));
    }

    public void addErrorMessage(String code, String message, String property) {
        errorMessages.add(new ErrorMessage(code, message, property));
    }
}
