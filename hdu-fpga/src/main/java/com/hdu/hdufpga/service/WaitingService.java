package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;

public interface WaitingService {
    UserConnectionVO userInQueue(String token) throws Exception;

    Result checkAvailability(String token) throws Exception;

    boolean freezeConnection(String token);
}
