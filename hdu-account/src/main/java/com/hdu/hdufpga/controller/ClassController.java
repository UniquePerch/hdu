package com.hdu.hdufpga.controller;

import com.hdu.controller.BaseController;
import com.hdu.entity.po.ClassPO;
import com.hdu.hdufpga.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/class")
@Slf4j
public class ClassController extends BaseController<ClassService,ClassPO>{

}
