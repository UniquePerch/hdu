package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.po.ChapterPO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ChapterControllerTest {
    @Resource
    private ChapterController chapterController;

    @Test
    void testChapterControllerAdd() {
        ChapterPO chapterPO = new ChapterPO();
        chapterPO.setIntro("Intro1");
        chapterPO.setProcess("Process1");
        chapterPO.setVideoPath("VideoPath1");
        chapterPO.setPptPath("PptPath1");
        chapterPO.setAnimatePath("AnimatePath1");
        chapterPO.setLinkFilePath("LinkFilePath1");
        chapterPO.setMark(1);
        chapterPO.setNumber(1);
        chapterPO.setTitle("Title1");
        System.out.println(chapterController.create(chapterPO));
    }

    @Test
    void testChapterControllerGet() {
        System.out.println(chapterController.get(1));
    }

    @Test
    void testChapterControllerUpdate() {
        ChapterPO chapterPO = new ChapterPO();
        chapterPO.setId(1);
        chapterPO.setIntro("Intro2");
        chapterPO.setProcess("Process2");
        chapterPO.setVideoPath("VideoPath2");
        chapterPO.setPptPath("PptPath2");
        chapterPO.setAnimatePath("AnimatePath2");
        chapterPO.setLinkFilePath("LinkFilePath2");
        chapterPO.setMark(2);
        chapterPO.setNumber(2);
        chapterPO.setTitle("Title2");
        System.out.println(chapterController.update(chapterPO));
    }

    @Test
    void testChapterControllerDelete() {
        ChapterPO chapterPO = new ChapterPO();
        chapterPO.setId(1);
        System.out.println(chapterController.delete(chapterPO));
    }

    @Test
    void testChapterControllerList() {
        System.out.println(chapterController.listPage(1, 2));
    }

    @Test
    void testChapterControllerRecordFinish() {
        System.out.println(chapterController.recordFinish(511, 2));
    }

    @Test
    void testChapterControllerGetAllChapterRecord() {
        System.out.println(chapterController.getAllChapterRecord(1, 2));
    }

    @Test
    void testChapterControllerGetChapterRecordByUserId() {
        System.out.println(chapterController.getChapterRecordByUserId(511));
    }
}
