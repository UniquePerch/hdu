package com.hdu.hdufpga.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.ResourcePO;
import com.hdu.hdufpga.mapper.ResourceMapper;
import com.hdu.hdufpga.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl extends MPJBaseServiceImpl<ResourceMapper, ResourcePO> implements ResourceService {
}
