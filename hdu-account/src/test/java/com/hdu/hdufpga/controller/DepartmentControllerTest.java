package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.DepartmentPO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class DepartmentControllerTest {
    @Resource
    private DepartmentController departmentController;

    @Test
    void testDepartmentControllerAdd() {
        DepartmentPO departmentPO = new DepartmentPO();
        departmentPO.setName("杭州电子科技大学testController");
        departmentPO.setFatherDepartment(0);
        departmentController.create(departmentPO);
    }

    @Test
    void testDepartmentControllerUpdate() {
        DepartmentPO departmentPO = new DepartmentPO();
        departmentPO.setId(4);
        departmentPO.setName("杭州电子科技大学testControllerUpdate");
        departmentPO.setFatherDepartment(2);
        departmentController.update(departmentPO);
    }

    @Test
    void testDepartmentControllerDelete() {
        DepartmentPO departmentPO = new DepartmentPO();
        departmentPO.setId(4);
        departmentController.delete(departmentPO);
    }

    @Test
    void testDepartmentControllerGet() {
        System.out.println(departmentController.get(4));
    }

    @Test
    void testDepartmentControllerGetPage() {
        System.out.println(departmentController.listPage(1, 2));
        System.out.println(departmentController.listPage(2, 2));
    }
}
