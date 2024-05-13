package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.ResourcePO;
import com.hdu.hdufpga.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
@Slf4j
public class ResourceController extends BaseController<ResourceService, ResourcePO> {
    //level >= 3
    @Override
    public Result create(@RequestBody ResourcePO resourcePO) {
        return super.create(resourcePO);
    }

    //level >= 3
    @Override
    public Result delete(@RequestBody ResourcePO resourcePO) {
        return super.delete(resourcePO);
    }

    //level >= 3
    @Override
    public Result update(@RequestBody ResourcePO resourcePO) {
        return super.update(resourcePO);
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
