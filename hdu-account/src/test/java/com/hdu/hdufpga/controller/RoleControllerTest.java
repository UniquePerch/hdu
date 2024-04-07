package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.RolePO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RoleControllerTest {
    @Resource
    private RoleController roleController;

    @Test
    void testRoleAdd() {
        RolePO rolePO = new RolePO();
        rolePO.setName("老师");
        rolePO.setPrivilegeCharacter("teacher");
        rolePO.setPrivilegeLevel(4);
        rolePO.setEnable(true);
        System.out.println(roleController.create(rolePO));
    }

    @Test
    void testRoleUpdate() {
        RolePO rolePO = new RolePO();
        rolePO.setId(3);
        rolePO.setName("老师");
        rolePO.setPrivilegeCharacter("teacher");
        rolePO.setPrivilegeLevel(4);
        rolePO.setEnable(true);
        System.out.println(roleController.update(rolePO));
    }

    @Test
    void testRoleGet() {
        System.out.println(roleController.get(3));
    }

    @Test
    void testRoleList() {
        System.out.println(roleController.listPage(1, 2));
    }
}
