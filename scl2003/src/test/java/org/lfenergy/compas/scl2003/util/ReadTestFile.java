// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2003.util;

import org.lfenergy.compas.scl2003.commons.Scl2003MarshallerWrapper;
import org.lfenergy.compas.scl2003.model.SCL;

public class ReadTestFile {
    public static SCL readSCL(String sclFilename) {
        var inputStream = ReadTestFile.class.getResourceAsStream("/scl/" + sclFilename);
        assert inputStream != null;
        return new Scl2003MarshallerWrapper.Builder().build().unmarshall(inputStream);
    }
}
