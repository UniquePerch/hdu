package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.KnowledgePO;
import com.hdu.hdufpga.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/knowledge")
@RestController
@Slf4j
public class KnowledgeController extends BaseController<KnowledgeService, KnowledgePO> {
    //level >= 3
    @Override
    public Result create(KnowledgePO knowledgePO) {
        return super.create(knowledgePO);
    }

    //level >= 3
    @Override
    public Result delete(KnowledgePO knowledgePO) {
        return super.delete(knowledgePO);
    }

    //level >= 3
    @Override
    public Result update(KnowledgePO knowledgePO) {
        return super.update(knowledgePO);
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
