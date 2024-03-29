package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.Result;

public interface WaitingService {
    Result userInQueue(String token);

    Result checkAvailability(String token);

    boolean freezeConnection(String token);
}
