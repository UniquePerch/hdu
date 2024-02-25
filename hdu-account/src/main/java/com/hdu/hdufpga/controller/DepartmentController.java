package com.hdu.hdufpga.controller;

import com.hdu.entity.po.DepartmentPO;
import com.hdu.hdufpga.service.DepartmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseController<DepartmentService, DepartmentPO>{
}
