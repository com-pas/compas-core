// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.lfenergy.compas.core.commons.model.ErrorResponse;

public class ErrorResponseDecoder extends AbstractDecoder<ErrorResponse> {
    @Override
    public boolean willDecode(String message) {
        return (message != null);
    }

    @Override
    public ErrorResponse decode(String message) {
        return WebsocketSupport.decode(message, ErrorResponse.class);
    }
}
