package com.hdu.hdufpga.exception;

import java.io.IOException;

public class InvalidFileSuffixException extends IOException {
    public InvalidFileSuffixException(String s) {
        super(s);
    }
}
