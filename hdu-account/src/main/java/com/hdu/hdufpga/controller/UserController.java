package com.hdu.hdufpga.controller;

import com.hdu.entity.po.UserPO;
import com.hdu.hdufpga.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserService, UserPO>{

}