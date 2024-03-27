package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.service.CircuitBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/co/fpga")
@Slf4j
public class CircuitBoardController extends BaseController<CircuitBoardService, CircuitBoardPO> {
}
