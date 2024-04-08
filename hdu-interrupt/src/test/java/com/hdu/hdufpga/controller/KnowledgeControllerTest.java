package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.KnowledgePO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class KnowledgeControllerTest {
    @Resource
    private KnowledgeController knowledgeController;

    @Test
    void testKnowledgeControllerAdd() {
        KnowledgePO knowledgePO = new KnowledgePO();
        knowledgePO.setKnowledgeContent("knowledge1");
        System.out.println(knowledgeController.create(knowledgePO));
    }

    @Test
    void testKnowledgeControllerDelete() {
        KnowledgePO knowledgePO = new KnowledgePO();
        knowledgePO.setId(1);
        System.out.println(knowledgeController.delete(knowledgePO));
    }

    @Test
    void testKnowledgeControllerUpdate() {
        KnowledgePO knowledgePO = new KnowledgePO();
        knowledgePO.setId(1);
        knowledgePO.setKnowledgeContent("knowledge2");
        System.out.println(knowledgeController.update(knowledgePO));
    }

    @Test
    void testKnowledgeControllerQuery() {
        System.out.println(knowledgeController.get(1));
    }
}
