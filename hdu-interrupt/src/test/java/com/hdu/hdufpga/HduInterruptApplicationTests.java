package com.hdu.hdufpga;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hdu.hdufpga.controller.ClassController;
import com.hdu.hdufpga.controller.PaperController;
import com.hdu.hdufpga.entity.BaseEntity;
import com.hdu.hdufpga.entity.po.ClassPO;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.mapper.ClassMapper;
import com.hdu.hdufpga.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
class HduInterruptApplicationTests {
    @Resource
    ClassMapper classMapper;

    @Test
    void contextLoads() {
        MPJLambdaWrapper<ClassPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .select(ClassPO::getCreateByUserId, ClassPO::getName, BaseEntity::getId, ClassPO::getIsOver, BaseEntity::getIsDeleted, BaseEntity::getCreateTime, BaseEntity::getUpdateTime)
                .selectAs(UserPO::getUsername, ClassPO::getCreateByUsername)
                .leftJoin(UserPO.class, UserPO::getId, ClassPO::getCreateByUserId)
        ;
        List<ClassPO> list = classMapper.selectJoinList(ClassPO.class, wrapper);
        System.out.println(list);
    }

    @Test
    void testCal() {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Resource
    PaperController paperController;

    @Test
    void testPaper() {
        System.out.println(paperController.getHandInInfoByClassId(1));
    }

    @Resource
    ClassController classController;

    @Test
    void testClass() {
        System.out.println(classController.getStudentListByClassId(1));
    }

    @DubboReference
    UserService userService;

    @Test
    void testDubbo() {
        ArrayList<String> objects = new ArrayList<>();
        objects.add("20042025");
        System.out.println(userService.getIdByUserName(objects));
    }
}
