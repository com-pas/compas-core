// SPDX-FileCopyrightText: 2022 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.websocket;

import org.lfenergy.compas.core.commons.model.ErrorResponse;

public class ErrorResponseEncoder extends AbstractJaxbEncoder<ErrorResponse> {
    public ErrorResponseEncoder() {
        super(ErrorResponse.class);
    }
}
