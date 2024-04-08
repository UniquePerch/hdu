package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.ClassPO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ClassControllerTest {
    @Resource
    private ClassController classController;

    @Test
    void testClassControllerAdd() {
        ClassPO classPO = new ClassPO();
        classPO.setName("班级2");
        classPO.setIsOver(false);
        classPO.setCreateByUserId(512);
        System.out.println(classController.create(classPO));
    }

    @Test
    void testClassControllerUpdate() {
        ClassPO classPO = new ClassPO();
        classPO.setId(3);
        classPO.setName("班级2");
        classPO.setIsOver(false);
        classPO.setCreateByUserId(512);
        System.out.println(classController.update(classPO));
    }

    @Test
    void testClassControllerDelete() {
        ClassPO classPO = new ClassPO();
        classPO.setId(3);
        System.out.println(classController.delete(classPO));
    }

    @Test
    void testClassControllerGet() {
        System.out.println(classController.get(3));
    }

    @Test
    void testClassControllerList() {
        System.out.println(classController.listPage(1, 2));
    }

    @Test
    void testClassControllerGetStudentListByClassId() {
        System.out.println(classController.getStudentListByClassId(3));
    }

    @Test
    void testClassControllerGetSortedClassListByTeacherId() {
        System.out.println(classController.getSortedClassListByTeacherId(512));
    }
}
