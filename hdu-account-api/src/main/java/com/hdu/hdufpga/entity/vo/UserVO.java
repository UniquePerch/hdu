package com.hdu.hdufpga.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private Integer privilegeLevel;
}
