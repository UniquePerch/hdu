package com.hdu.hdufpga.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.entity.po.DepartmentPO;
import com.hdu.hdufpga.mapper.DepartmentMapper;
import com.hdu.hdufpga.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentPO> implements DepartmentService {
}
