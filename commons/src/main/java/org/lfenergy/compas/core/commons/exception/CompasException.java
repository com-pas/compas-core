// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.core.commons.exception;

public class CompasException extends RuntimeException {
    private final String errorCode;

    public CompasException(String errorCode, String msg) {
        super(msg);

        this.errorCode = errorCode;
    }

    public CompasException(String errorCode, String msg, Throwable throwable) {
        super(msg, throwable);

        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return super.toString() + errorCode + " -> " + super.toString();
    }
}
