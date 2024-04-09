package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.TestRecordPO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TestRecordControllerTest {
    @Resource
    private TestRecordController testRecordController;

    @Test
    void testTestRecordControllerAdd() {
        System.out.println(testRecordController.create(new TestRecordPO()));
    }

    @Test
    void testTestRecordControllerGetMaxScore() {
        System.out.println(testRecordController.getMaxScore(511, 3));
    }
}
