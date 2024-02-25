package com.hdu.hdufpga.entity.vo;

import com.hdu.hdufpga.entity.BaseEntity;
import lombok.*;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO extends BaseEntity {
    private String username;
    private String password;
    private String realName;
    private Long activeTime;
    private String department;
    private Integer userLevel;
}
