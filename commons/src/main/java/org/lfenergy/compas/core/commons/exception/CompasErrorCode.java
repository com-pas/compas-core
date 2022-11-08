// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.exception;

public class CompasErrorCode {
    CompasErrorCode() {
        throw new UnsupportedOperationException("CompasErrorCode class");
    }

    public static final String NO_CLASS_LOADER_ERROR_CODE = "CORE-0001";
    public static final String RESOURCE_NOT_FOUND_ERROR_CODE = "CORE-0002";
    public static final String CREATION_ERROR_CODE = "CORE-0003";
    public static final String CONFIGURATION_ERROR_CODE = "CORE-0004";
    public static final String PROPERTY_ERROR_ERROR_CODE = "CORE-0005";
    public static final String UNMARSHAL_ERROR_CODE = "CORE-0006";
    public static final String MARSHAL_ERROR_CODE = "CORE-0007";
    public static final String INVALID_YML_ERROR_CODE = "CORE-0008";

    public static final String WEBSOCKET_DECODER_ERROR_CODE = "CORE-0100";
    public static final String WEBSOCKET_ENCODER_ERROR_CODE = "CORE-0101";
    public static final String WEBSOCKET_GENERAL_ERROR_CODE = "CORE-0102";

    public static final String CONVERT_TO_STRING_ERROR = "CORE-1000";
    public static final String CONVERT_TO_ELEMENT_ERROR = "CORE-1001";

    public static final String VALIDATION_ERROR = "CORE-8000";

    public static final String UNKNOWN_EXCEPTION_ERROR = "CORE-9999";
}
