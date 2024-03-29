package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;


public interface CircuitBoardService extends MPJBaseService<CircuitBoardPO> {
    CircuitBoardPO getAFreeCircuitBoard();

    String freeCircuitBoard(String longId);
}
