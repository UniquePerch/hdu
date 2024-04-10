package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.vo.UserResourceRecordVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class StudentStudyRecordControllerTest {
    @Resource
    private StudentStudyRecordController studentStudyRecordController;

    @Test
    void testStudentStudyRecordControllerUpdateResourceRecord() {
        UserResourceRecordVO userResourceRecordVO = new UserResourceRecordVO();
        userResourceRecordVO.setUserId(1);
        userResourceRecordVO.setResourceId(1);
        userResourceRecordVO.setDuration(199L);
        studentStudyRecordController.updateResourceRecord(userResourceRecordVO);
    }

    @Test
    void testStudentStudyRecordControllerGetAllRecord() {
        System.out.println(studentStudyRecordController.getRecordByClass(1));
    }
}
