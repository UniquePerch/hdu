package com.hdu.hdufpga.service;


import com.hdu.hdufpga.entity.po.UserPO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.*;

@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void testUserServiceAdd() {
        UserPO userPO = new UserPO();
        userPO.setUsername("testUsernameService");
        userPO.setPassword("testPassword");
        userPO.setRealName("测试用户2");
        userPO.setUserRoleId(3);
        userPO.setUserDepartmentId(4);
        userService.save(userPO);
    }

    @Test
    void testUserServiceUpdate() {
        UserPO userPO = new UserPO();
        userPO.setId(508);
        userPO.setUsername("testUsernameService1");
        userPO.setPassword("testPassword123");
        userPO.setRealName("测试用户2");
        userPO.setUserRoleId(3);
        userPO.setUserDepartmentId(4);
        userService.updateById(userPO);
    }

    @Test
    void testUserServiceList() {
        System.out.println(userService.list());
    }

    @Test
    void testUserServiceDelete() {
        userService.removeById(508);
    }

    @Test
    void testUserServiceFindByUsername() {
        System.out.println(userService.getUserByUserName("testUsername", 3));
    }

    @Test
    void testUserServiceGetIdByUserName() {
        List<String> list = new ArrayList<>();
        list.add("testUsername");
        list.add("testUsername2");
        System.out.println(userService.getIdByUserName(list, 4));
    }

    @Test
    void testUserServiceGetUserCountByDate() {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.set(1900, Calendar.JANUARY, 1);
        Date startDate = startCalendar.getTime();
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.set(2050, Calendar.JANUARY, 1);
        Date endDate = endCalendar.getTime();
        System.out.println(userService.getUserCountByDate(startDate, endDate));
    }
}
