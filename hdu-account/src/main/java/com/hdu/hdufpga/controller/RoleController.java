package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.RolePO;
import com.hdu.hdufpga.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController<RoleService, RolePO> {

}
