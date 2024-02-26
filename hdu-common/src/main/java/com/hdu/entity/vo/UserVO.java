package com.hdu.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hdu.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO extends BaseEntity {
    @ExcelProperty("学号")
    private String username;
    private String password;
    @ExcelProperty("姓名")
    private String realName;
    private Long activeTime;
    private Integer userDepartmentId;
    private String userDepartmentName;
    private Integer userRoleId;
    private String userRoleName;
}
