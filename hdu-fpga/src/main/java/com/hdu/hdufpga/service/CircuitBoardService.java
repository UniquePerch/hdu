package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.exception.CircuitBoardException;


public interface CircuitBoardService extends MPJBaseService<CircuitBoardPO> {
    CircuitBoardPO getAFreeCircuitBoard() throws CircuitBoardException;

    String freeCircuitBoard(String longId);

    Long getFreeCircuitBoardCount();

    CircuitBoardPO getByCBLongId(String longId);

    Integer updateByLongId(CircuitBoardPO circuitBoardPO);
}
