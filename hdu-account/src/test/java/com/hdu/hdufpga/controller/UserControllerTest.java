package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserControllerTest {
    @Resource
    private UserController userController;

    @Test
    void testUserControllerCreate() {
        UserPO userPO = new UserPO();
        userPO.setUsername("testUsername");
        userPO.setPassword("testPassword");
        userPO.setRealName("测试用户2");
        userPO.setUserRoleId(3);
        userPO.setUserDepartmentId(4);
        System.out.println(userController.create(userPO));
    }

    @Test
    void testUserControllerUpdate() {
        UserPO userPO = new UserPO();
        userPO.setId(503);
        userPO.setUsername("testUsername");
        userPO.setPassword("testPassword1");
        userPO.setRealName("测试用户1");
        userPO.setUserRoleId(4);
        userPO.setUserDepartmentId(4);
        System.out.println(userController.update(userPO));
    }

    @Test
    void testUserControllerGetPage() {
        System.out.println(userController.listPage(1, 2));
    }

    @Test
    void testUserControllerGet() {
        System.out.println(userController.get(503));
    }

    @Test
    void testUserControllerDelete() {
        UserPO userPO = new UserPO();
        userPO.setId(503);
        System.out.println(userController.delete(userPO));
    }
}
