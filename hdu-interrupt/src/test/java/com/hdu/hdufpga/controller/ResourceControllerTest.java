package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.ResourcePO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResourceControllerTest {

    @Autowired
    private ResourceController resourceController;

    @Test
    void testResourceControllerAdd() {
        ResourcePO resourcePO = new ResourcePO();
        resourcePO.setResourceName("资源2");
        System.out.println(resourceController.create(resourcePO));
    }

    @Test
    void testResourceControllerUpdate() {
        ResourcePO resourcePO = new ResourcePO();
        resourcePO.setId(1);
        resourcePO.setResourceName("资源资源资源");
        System.out.println(resourceController.update(resourcePO));
    }

    @Test
    void testResourceControllerDelete() {
        ResourcePO resourcePO = new ResourcePO();
        resourcePO.setId(1);
        System.out.println(resourceController.delete(resourcePO));
    }

    @Test
    void testResourceControllerGet() {
        System.out.println(resourceController.get(2));
    }

    @Test
    void testResourceControllerList() {
        System.out.println(resourceController.listPage(1, 2));
    }
}
