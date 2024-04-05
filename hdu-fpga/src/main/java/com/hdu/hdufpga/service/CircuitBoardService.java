package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.exception.CircuitBoardException;

import java.sql.SQLException;


public interface CircuitBoardService extends MPJBaseService<CircuitBoardPO> {
    CircuitBoardPO getAFreeCircuitBoard() throws CircuitBoardException;

    CircuitBoardPO getCircuitBoardByIp(String ip);

    String freeCircuitBoard(String cbIp);

    Long getFreeCircuitBoardCount();

    CircuitBoardPO getByCBLongId(String longId);

    Integer updateByLongId(CircuitBoardPO circuitBoardPO);

    void deleteByLongId(String longId);

    void recordBitToBitForTheFirstTime(String token, String filePath);

    Boolean clearUserRedisAndFreeCB(String token) throws CircuitBoardException, SQLException;

    Boolean getRecordStatus(String token, String cbIp) throws CircuitBoardException;

    Boolean sendButtonString(String token, String switchButtonStatus, String tapButtonStatus) throws CircuitBoardException;

    String getLightString(String token) throws CircuitBoardException;

    String getNixieTubeString(String token) throws CircuitBoardException;

    String getProcessedBtnStr(String token) throws CircuitBoardException;
}
