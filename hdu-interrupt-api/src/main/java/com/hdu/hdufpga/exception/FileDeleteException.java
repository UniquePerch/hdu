package com.hdu.hdufpga.exception;

import java.nio.file.FileSystemException;

public class FileDeleteException extends FileSystemException {
    public FileDeleteException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
