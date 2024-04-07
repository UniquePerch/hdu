package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.po.DepartmentPO;
import com.hdu.hdufpga.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class DepartmentServiceTest {
    @Resource
    private DepartmentService departmentService;

    @Test
    public void testDepartmentServiceAdd() {
        DepartmentPO departmentPO = new DepartmentPO();
        departmentPO.setName("杭州电子科技大学ServiceTest");
        departmentPO.setFatherDepartment(0);
        departmentPO.setCreateTime(TimeUtil.getNowTime());
        departmentPO.setUpdateTime(TimeUtil.getNowTime());
        departmentService.save(departmentPO);
    }

    @Test
    public void testDepartmentServiceUpdate() {
        DepartmentPO departmentPO = new DepartmentPO();
        departmentPO.setId(5);
        departmentPO.setName("杭州电子科技大学ServiceTestUpdate");
        departmentPO.setFatherDepartment(1);
        departmentPO.setUpdateTime(TimeUtil.getNowTime());
        departmentService.updateById(departmentPO);
    }

    @Test
    public void testDepartmentServiceGet() {
        System.out.println(departmentService.getById(5));
    }

    @Test
    public void testDepartmentServiceDelete() {
        departmentService.removeById(5);
    }

    @Test
    public void testDepartmentServiceList() {
        departmentService.list().forEach(System.out::println);
    }
}
