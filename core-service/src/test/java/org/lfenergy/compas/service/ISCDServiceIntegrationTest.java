// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.service;


import org.lfenergy.compas.commons.MarshallerWrapper;
import org.lfenergy.compas.exception.ScdException;
import org.lfenergy.compas.scl.SCL;
import org.lfenergy.compas.scl.THitem;
import org.lfenergy.compas.testhelpers.SimpleSCD;
import org.lfenergy.compas.testhelpers.SimpleSCDService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ISCDServiceIntegrationTest {

    private final String FILE_NAME = "file.scd";
    private final String H_VERSION = "1.0";
    private final String H_REVISION = "1.0";
    private final String WHO = "WHO";
    private final String WHY = "WHY";
    private final String WHAT = "WHAT";
    private final UUID ID = UUID.randomUUID();


    @Autowired
    private SimpleSCDService simpleSCDService;

    @Autowired
    private MarshallerWrapper marshallerWrapper;

    @Test
    public void shouldReturnOKWhenInitiateSCD() {
        final SimpleSCD result = simpleSCDService.initiateSCD(FILE_NAME, H_VERSION, H_REVISION);
        SCL resultScd = marshallerWrapper.unmarshall(result.getRawXml());
        assertAll("SimpleSCD",
                () -> assertNotNull(result),
                () -> assertNotNull(result.getId()),
                () -> assertNotNull(resultScd.getHeader()),
                () -> assertEquals(result.getId(),
                        UUID.fromString(resultScd.getHeader().getId()))
        );
    }

    @Test
    public void shouldReturnOKWhenAddHistoryItem() throws ScdException {
        final SimpleSCD result = simpleSCDService.initiateSCD(FILE_NAME, H_VERSION, H_REVISION);

        SimpleSCD resultWithHistory = simpleSCDService.addHistoryItem(result, WHO, WHAT, WHY);
        SCL resultScdWithHistory = marshallerWrapper.unmarshall(resultWithHistory.getRawXml());
        assertAll("SimpleSCD",
                () -> assertNotNull(resultWithHistory),
                () -> assertNotNull(resultWithHistory.getId()),
                () -> assertNotNull(resultScdWithHistory.getHeader()),
                () -> assertEquals(result.getId(),
                        UUID.fromString(resultScdWithHistory.getHeader().getId())),
                () -> assertNotNull(resultScdWithHistory.getHeader().getHistory()),
                () -> assertFalse(resultScdWithHistory.getHeader().getHistory().getHitem().isEmpty())
        );
        THitem tHitem = resultScdWithHistory.getHeader().getHistory().getHitem().get(0);

        assertAll("SimpleSCD with history",
                () -> assertNotNull(tHitem),
                () -> assertEquals(tHitem.getRevision(),resultScdWithHistory.getHeader().getRevision()),
                () -> assertEquals(tHitem.getVersion(),resultScdWithHistory.getHeader().getVersion()),
                () -> assertEquals(tHitem.getWhat(),WHAT),
                () -> assertEquals(tHitem.getWhy(),WHY),
                () -> assertEquals(tHitem.getWho(),WHO),
                () -> assertNotNull(tHitem.getWhen())
        );
    }
}
