package org.lfenergy.compas.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScdExceptionTest {

    @Test
    public void testScdException(){
        final String MSG = "This is an exception";
        ScdException scdException = new ScdException(MSG);

        assertAll("SCD_EXCEPTION",
                () -> assertTrue(scdException instanceof Exception),
                () -> assertEquals(MSG,scdException.getMessage())
        );
    }
}