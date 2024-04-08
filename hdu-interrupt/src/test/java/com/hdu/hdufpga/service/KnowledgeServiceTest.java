package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.po.KnowledgePO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class KnowledgeServiceTest {
    @Resource
    private KnowledgeService knowledgeService;

    @Test
    void testKnowledgeServiceAdd() {
        KnowledgePO knowledgePO = new KnowledgePO();
        knowledgePO.setKnowledgeContent("knowledgeContent");
        knowledgeService.save(knowledgePO);
    }

    @Test
    void testKnowledgeServiceDelete() {
        knowledgeService.removeById(2);
    }

    @Test
    void testKnowledgeServiceUpdate() {
        KnowledgePO knowledgePO = new KnowledgePO();
        knowledgePO.setId(2);
        knowledgePO.setKnowledgeContent("knowledgeContentUpdate");
        knowledgePO.setUpdateTime(new Date());
        knowledgePO.setCreateTime(new Date());
        knowledgeService.updateById(knowledgePO);
    }

    @Test
    void testKnowledgeServiceQuery() {
        knowledgeService.list().forEach(System.out::println);
    }
}
