package com.hdu.hdufpga.exception;

import java.io.IOException;

public class EmptyHistoryStepsException extends IOException {
    public EmptyHistoryStepsException(String message) {
        super(message);
    }
}
