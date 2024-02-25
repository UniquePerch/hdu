package com.hdu.entity.vo;

import com.hdu.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
