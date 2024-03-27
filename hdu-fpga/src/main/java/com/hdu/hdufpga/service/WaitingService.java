package com.hdu.hdufpga.service;

public interface WaitingService {
    Long getRankInQueue(String token);

    Boolean userInQueue(String token);
}
