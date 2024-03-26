package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.vo.UserVisitInfoVO;

import java.util.Date;

public interface UserRecordService {
    UserVisitInfoVO getUserVisitInfo(Date startDate, Date endDate);
}
