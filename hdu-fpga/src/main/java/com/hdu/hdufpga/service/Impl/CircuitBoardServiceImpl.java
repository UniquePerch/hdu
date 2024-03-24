package com.hdu.hdufpga.service.Impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.mapper.CircuitBoardMapper;
import com.hdu.hdufpga.service.CircuitBoardService;
import org.springframework.stereotype.Service;

@Service
public class CircuitBoardServiceImpl extends MPJBaseServiceImpl<CircuitBoardMapper, CircuitBoardPO> implements CircuitBoardService {
}
