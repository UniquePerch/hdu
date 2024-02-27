package com.hdu.hdufpga;

import cn.hutool.core.codec.Base64Decoder;
import com.hdu.hdufpga.controller.DepartmentController;
import com.hdu.hdufpga.controller.RoleController;
import com.hdu.hdufpga.controller.UserController;
import com.hdu.hdufpga.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

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
    void testRole(){
        String s = "JXU2MjExJXU3MjMxJXU0RjYw";
        String hex = Base64Decoder.decodeStr(s);
        System.out.println(hex);
        //25105
    }
}
