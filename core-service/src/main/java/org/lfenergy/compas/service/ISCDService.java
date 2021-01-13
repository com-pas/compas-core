// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.service;

import org.lfenergy.compas.exception.ScdException;
import org.lfenergy.compas.scl.SCL;

import java.util.UUID;

public interface ISCDService<T> {
    T initiateSCD(String filename, String hVersion, String hRevision) ;
    T addHistoryItem(UUID id, String who, String when, String why) throws ScdException;
    T addHistoryItem(T scdObj, String who, String when, String why) throws ScdException;
    void addIED(UUID id, SCL icd);
    void addIED(UUID id, byte[] icd);
}
