package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.DepartmentPO;
import com.hdu.hdufpga.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController extends BaseController<DepartmentService, DepartmentPO> {
    //level >= 3
    @Override
    public Result create(DepartmentPO departmentPO) {
        return super.create(departmentPO);
    }

    //level >= 3
    @Override
    public Result delete(DepartmentPO departmentPO) {
        return super.delete(departmentPO);
    }

    //level >= 3
    @Override
    public Result update(DepartmentPO departmentPO) {
        return super.update(departmentPO);
    }

    //level >= 3
    @Override
    public Result get(Integer id) {
        return super.get(id);
    }

    //level >= 3
    @Override
    public Result listPage(Integer current, Integer size) {
        return super.listPage(current, size);
    }
}
