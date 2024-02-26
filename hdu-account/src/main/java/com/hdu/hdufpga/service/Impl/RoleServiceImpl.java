package com.hdu.hdufpga.service.Impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.entity.po.RolePO;
import com.hdu.hdufpga.mapper.RoleMapper;
import com.hdu.hdufpga.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends MPJBaseServiceImpl<RoleMapper, RolePO> implements RoleService {
}
