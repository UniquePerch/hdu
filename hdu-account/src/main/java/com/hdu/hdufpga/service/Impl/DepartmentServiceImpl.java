package com.hdu.hdufpga.service.Impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.DepartmentPO;
import com.hdu.hdufpga.mapper.DepartmentMapper;
import com.hdu.hdufpga.service.DepartmentService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class DepartmentServiceImpl extends MPJBaseServiceImpl<DepartmentMapper, DepartmentPO> implements DepartmentService {
}
