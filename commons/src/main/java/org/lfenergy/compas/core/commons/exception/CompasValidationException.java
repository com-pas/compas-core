// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.exception;

public class CompasValidationException extends CompasException {
    public CompasValidationException(String errorCode, String msg) {
        super(errorCode, msg);
    }

    public CompasValidationException(String errorCode, String msg, Throwable throwable) {
        super(errorCode, msg, throwable);
    }
}
