package com.hdu.hdufpga;

import com.hdu.hdufpga.controller.DepartmentController;
import com.hdu.hdufpga.controller.RoleController;
import com.hdu.hdufpga.controller.UserController;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@Slf4j
class HduAccountApplicationTests {

    @Resource
    UserService userService;

    @Resource
    UserController userController;

    @Test
    void testUser() {
    }


    @Resource
    DepartmentController departmentController;

    @Test
    void testDepartment() {

    }

    @Resource
    RoleController roleController;

    @Test
    void testRole() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserPO userPO = new UserPO();
        userPO.setUserRoleId(1);
        userPO.setRealName("杨阳");
        userPO.setUsername("1919810");
        userPO.setPassword(bCryptPasswordEncoder.encode("114514"));
        userPO.setUserDepartmentId(1);
        userPO.setCreateTime(new Date());
        userPO.setUpdateTime(new Date());
        userService.save(userPO);
    }
}
