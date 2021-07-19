// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2007b4.util;

import org.lfenergy.compas.scl2007b4.commons.Scl2007b4MarshallerWrapper;
import org.lfenergy.compas.scl2007b4.model.SCL;

public class ReadTestFile {
    public static SCL readSCL(String sclFilename) {
        var inputStream = ReadTestFile.class.getResourceAsStream("/scl/" + sclFilename);
        assert inputStream != null;
        return new Scl2007b4MarshallerWrapper.Builder().build().unmarshall(inputStream);
    }
}
