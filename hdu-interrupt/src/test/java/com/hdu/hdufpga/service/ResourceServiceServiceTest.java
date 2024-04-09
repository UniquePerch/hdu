package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.po.ResourcePO;
import com.hdu.hdufpga.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ResourceServiceServiceTest {
    @Resource
    ResourceService resourceService;


    @Test
    void testResourceServiceAdd() {
        ResourcePO resourcePO = new ResourcePO();
        resourcePO.setResourceName("资源3");
        resourcePO.setCreateTime(TimeUtil.getNowTime());
        resourcePO.setUpdateTime(TimeUtil.getNowTime());
        System.out.println(resourceService.save(resourcePO));
    }

    @Test
    void testResourceServiceUpdate() {
        ResourcePO resourcePO = new ResourcePO();
        resourcePO.setId(3);
        resourcePO.setResourceName("资源");
        resourcePO.setUpdateTime(TimeUtil.getNowTime());
        resourceService.updateById(resourcePO);
    }

    @Test
    void testResourceServiceDelete() {
        ResourcePO resourcePO = new ResourcePO();
        resourcePO.setId(3);
        resourceService.removeById(resourcePO.getId());
    }

    @Test
    void testResourceServiceFindAll() {
        List<ResourcePO> list = resourceService.list();
        list.forEach(System.out::println);
    }
}
