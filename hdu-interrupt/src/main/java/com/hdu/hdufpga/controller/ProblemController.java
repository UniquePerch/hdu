package com.hdu.hdufpga.controller;

import com.hdu.controller.BaseController;
import com.hdu.hdufpga.entity.po.Problem1PO;
import com.hdu.hdufpga.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/co/problem")
@Slf4j
public class ProblemController extends BaseController<ProblemService, Problem1PO> {

}
