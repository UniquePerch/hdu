package com.hdu.hdufpga;

import com.hdu.hdufpga.controller.UserController;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HduFpgaApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    UserController userController;
    @Test
    void contextLoads() {
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

}
