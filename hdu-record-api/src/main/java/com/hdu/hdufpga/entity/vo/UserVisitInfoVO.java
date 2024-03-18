package com.hdu.hdufpga.entity.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class UserVisitInfoVO {
    Long visitCount;

    Long studyDuration;

    Long userCount;
}
