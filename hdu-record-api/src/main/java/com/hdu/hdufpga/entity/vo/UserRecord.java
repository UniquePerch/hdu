package com.hdu.hdufpga.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserRecord {

    String userRealName;

    String className;

    Double maxTestScore;

    Long testCount;

    List<UserResourceRecordVO> userResourceRecordVOList;
}
