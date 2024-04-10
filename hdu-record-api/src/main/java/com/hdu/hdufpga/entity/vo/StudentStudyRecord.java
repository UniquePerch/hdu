package com.hdu.hdufpga.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentStudyRecord {
    String username;

    String userRealName;

    String className;

    Double maxTestScore;

    Long testCount;

    List<UserResourceRecordVO> userResourceRecord;
}
