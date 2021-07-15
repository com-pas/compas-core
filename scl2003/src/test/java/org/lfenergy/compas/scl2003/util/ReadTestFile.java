// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl2003.util;

import org.lfenergy.compas.scl2003.commons.MarshallerWrapper;
import org.lfenergy.compas.scl2003.model.SCL;

public class ReadTestFile {
    public static SCL readSCL(String sclFilename) throws Exception {
        var inputStream = ReadTestFile.class.getResourceAsStream("/scl/" + sclFilename);
        assert inputStream != null;
        return new MarshallerWrapper.Builder().build().unmarshall(inputStream);
    }
}
