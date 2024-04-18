package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController<UserService, UserPO> {

    //level >= 2
    @Override
    public Result create(UserPO userPO) {
        return super.create(userPO);
    }

    //level >= 3
    @Override
    public Result delete(UserPO userPO) {
        return super.delete(userPO);
    }

    //level >= 3
    @Override
    public Result update(UserPO userPO) {
        return super.update(userPO);
    }

    //level >= 2
    @Override
    public Result get(Integer id) {
        return super.get(id);
    }

    //level >= 2
    @Override
    public Result listPage(Integer current, Integer size) {
        return super.listPage(current, size);
    }
}
