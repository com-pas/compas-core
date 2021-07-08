// SPDX-FileCopyrightText: 2021 Alliander N.V.
//
// SPDX-License-Identifier: Apache-2.0
package org.lfenergy.compas.scl.util;

import org.lfenergy.compas.scl.commons.MarshallerWrapper;
import org.lfenergy.compas.scl.model.SCL;

public class ReadTestFile {
    public static SCL readSCL(String sclFilename) throws Exception {
        var inputStream = ReadTestFile.class.getResourceAsStream("/scl/" + sclFilename);
        assert inputStream != null;
        return new MarshallerWrapper.Builder().build().unmarshall(inputStream);
    }
}