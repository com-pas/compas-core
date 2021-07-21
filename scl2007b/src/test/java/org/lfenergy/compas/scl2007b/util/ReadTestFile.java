// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2007b.util;

import org.lfenergy.compas.scl2007b.commons.Scl2007bMarshallerWrapper;
import org.lfenergy.compas.scl2007b.model.SCL;

public class ReadTestFile {
    public static SCL readSCL(String sclFilename) {
        var inputStream = ReadTestFile.class.getResourceAsStream("/scl/" + sclFilename);
        assert inputStream != null;
        return new Scl2007bMarshallerWrapper.Builder().build().unmarshall(inputStream);
    }
}
