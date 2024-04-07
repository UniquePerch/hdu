package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.DepartmentPO;
import com.hdu.hdufpga.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController extends BaseController<DepartmentService, DepartmentPO> {

}
