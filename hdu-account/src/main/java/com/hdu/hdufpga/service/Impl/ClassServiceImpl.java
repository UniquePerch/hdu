package com.hdu.hdufpga.service.Impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.entity.po.ClassPO;
import com.hdu.hdufpga.mapper.ClassMapper;
import com.hdu.hdufpga.service.ClassService;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl extends MPJBaseServiceImpl<ClassMapper,ClassPO> implements ClassService {
}
