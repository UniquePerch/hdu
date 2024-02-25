package com.hdu.hdufpga;

import com.hdu.entity.po.DepartmentPO;
import com.hdu.entity.po.UserPO;
import com.hdu.hdufpga.controller.DepartmentController;
import com.hdu.hdufpga.controller.UserController;
import com.hdu.hdufpga.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class HduAccountApplicationTests {

    @Resource
    UserService userService;

    @Resource
    UserController userController;
    @Test
    void testUser() {
        UserPO userPO = UserPO.builder()
                .username("lyh")
                .password("yy5201314")
                .activeTime(123123123L)
                .realName("杨阳小姐的狗")
                .userLevel(2)
                .department("下沙小清华")
                .build();

        userPO.setId(3);
        System.out.println(userController.get(3));
    }


    @Resource
    DepartmentController departmentController;
    @Test
    void testDepartment() {
        DepartmentPO departmentPO = DepartmentPO.builder()
                .name("杭电")
                .fatherDepartment(0)
                .build();
        System.out.println(departmentController.create(departmentPO));
    }
}
