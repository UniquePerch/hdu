package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/co/user")
@Slf4j
public class UserController extends BaseController<UserService, UserPO> {
    @RequestMapping("/getIdByUserName")
    public List<Integer> getIdByUserName(@RequestBody List<String> userNameList){
        return service.getIdByUserName(userNameList);
    }
}
