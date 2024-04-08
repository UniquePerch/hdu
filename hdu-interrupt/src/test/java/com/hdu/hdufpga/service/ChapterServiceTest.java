package com.hdu.hdufpga.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ChapterServiceTest {
    @Resource
    private ChapterService chapterService;

    @Test
    void testChapterServiceGetAllChapterRecord() {
        chapterService.getAllChapterRecord(1, 2);
    }

    @Test
    void testChapterServiceGetChapterRecord() {
        chapterService.getChapterRecordByUserId(511).forEach(System.out::println);
        chapterService.getChapterRecordByUserId(510).forEach(System.out::println);
    }

    @Test
    void testChapterServiceRecordFinish() {
        System.out.println(chapterService.recordFinish(509, 1));
    }
}
