package com.hdu.hdufpga.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConnectionVO {
    private String token;
    private Date createDate;
    private Date updateDate;
    private Long leftSecond;
    private Boolean isFrozen;
    private String userIp;
    private String cbIp;
    private String longId;
}
