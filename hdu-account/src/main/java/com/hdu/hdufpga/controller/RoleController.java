package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.RolePO;
import com.hdu.hdufpga.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController extends BaseController<RoleService, RolePO> {
    //level >= 3
    @Override
    public Result create(@RequestBody RolePO rolePO) {
        return super.create(rolePO);
    }

    //level >= 3
    @Override
    public Result delete(RolePO rolePO) {
        return super.delete(rolePO);
    }

    //level >= 3
    @Override
    public Result update(RolePO rolePO) {
        return super.update(rolePO);
    }

    //level >= 3
    @Override
    public Result get(Integer id) {
        return super.get(id);
    }

    //level >= 3
    @Override
    public Result listPage(Integer current, Integer size) {
        return super.listPage(current, size);
    }
}
