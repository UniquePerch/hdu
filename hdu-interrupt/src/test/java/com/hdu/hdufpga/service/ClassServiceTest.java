package com.hdu.hdufpga.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ClassServiceTest {
    @Resource
    private ClassService classService;

    @Test
    void testClassServiceGetSortedClassList() {
        classService.getSortedClassList(512).forEach(System.out::println);
    }

    @Test
    void testClassServiceGetStudentListByClassId() {
        classService.getStudentListByClassId(3).forEach(System.out::println);
    }
}
