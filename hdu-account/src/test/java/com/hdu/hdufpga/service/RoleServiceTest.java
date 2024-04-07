package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.po.RolePO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RoleServiceTest {
    @Resource
    private RoleService roleService;

    @Test
    void testRoleServiceAdd() {
        RolePO rolePO = new RolePO();
        rolePO.setName("学生");
        rolePO.setPrivilegeLevel(3);
        rolePO.setPrivilegeCharacter("student");
        rolePO.setEnable(true);
        System.out.println(roleService.save(rolePO));
    }

    @Test
    void testRoleServiceUpdate() {
        RolePO rolePO = new RolePO();
        rolePO.setName("学生");
        rolePO.setId(4);
        rolePO.setPrivilegeLevel(3);
        rolePO.setPrivilegeCharacter("student1");
        rolePO.setEnable(false);
        System.out.println(roleService.updateById(rolePO));
    }

    @Test
    void testRoleServiceGet() {
        System.out.println(roleService.getById(4));
    }

    @Test
    void testRoleServiceGetList() {
        System.out.println(roleService.list());
    }

    @Test
    void testRoleServiceDelete() {
        System.out.println(roleService.removeById(4));
    }
}
