package com.hdu.hdufpga.service;

import com.hdu.hdufpga.exception.EmptyHistoryStepsException;

public interface CircuitBoardHistoryOperationService {
    void clearSteps(String token);

    Boolean loadOperationHistory(String token) throws EmptyHistoryStepsException;

    void saveOperationStep(String token, String step);
}
