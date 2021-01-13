// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.domain;


import org.junit.jupiter.api.Test;
import org.lfenergy.compas.testhelpers.SimpleSCD;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScdBomTest {

    private static final String FILE_NAME = "FileName.scd";
    private static final byte[] RAW_XML = "<SCL/>".getBytes();

    @Test
    public void testObjectConstruction(){

        SimpleSCD simpleSCD = new SimpleSCD();

        UUID randUUID = UUID.randomUUID();

        simpleSCD.setFileName(FILE_NAME);
        simpleSCD.setId(randUUID);
        simpleSCD.setRawXml(RAW_XML);

        assertEquals(FILE_NAME,simpleSCD.getFileName());
        assertEquals(RAW_XML,simpleSCD.getRawXml());
        assertEquals(randUUID,simpleSCD.getId());
    }

}