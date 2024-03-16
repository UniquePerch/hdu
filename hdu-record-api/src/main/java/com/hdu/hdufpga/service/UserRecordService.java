package com.hdu.hdufpga.service;

import com.hdu.hdufpga.entity.vo.UserRecord;

import java.util.List;

public interface UserRecordService {
    List<UserRecord> getRecordByClass(Integer classId);
}
