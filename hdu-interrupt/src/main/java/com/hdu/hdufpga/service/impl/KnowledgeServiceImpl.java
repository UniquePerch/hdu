package com.hdu.hdufpga.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.KnowledgePO;
import com.hdu.hdufpga.mapper.KnowledgeMapper;
import com.hdu.hdufpga.service.KnowledgeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class KnowledgeServiceImpl extends MPJBaseServiceImpl<KnowledgeMapper, KnowledgePO> implements KnowledgeService {
}
