package com.hdu.hdufpga.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootTest
public class UserVisitRecordControllerTest {
    @Resource
    private UserVisitRecordController userVisitRecordController;

    @Test
    void testUserVisitRecordControllerGetUserVisitInfo() {
        Calendar start = new GregorianCalendar();
        start.set(1919, Calendar.SEPTEMBER, 10);
        Date startDate = start.getTime();

        Calendar end = new GregorianCalendar();
        end.set(2025, Calendar.SEPTEMBER, 10);
        Date endDate = end.getTime();

        System.out.println(userVisitRecordController.getUserVisitInfo(startDate, endDate));
    }

}
