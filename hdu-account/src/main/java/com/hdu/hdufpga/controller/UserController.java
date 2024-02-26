package com.hdu.hdufpga.controller;

import com.hdu.controller.BaseController;
import com.hdu.entity.po.UserPO;
import com.hdu.hdufpga.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController extends BaseController<UserService, UserPO> {

}
