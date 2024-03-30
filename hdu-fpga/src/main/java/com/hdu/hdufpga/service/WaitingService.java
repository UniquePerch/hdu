package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.exception.UserQueueException;

public interface WaitingService {
    UserConnectionVO userInQueue(String token) throws UserQueueException;

    Result checkAvailability(String token) throws UserQueueException;

    boolean freezeConnection(String token);
}
