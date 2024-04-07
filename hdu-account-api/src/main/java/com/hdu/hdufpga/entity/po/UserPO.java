package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPO extends BaseEntity {
    private String username;
    private String password;
    private String realName;
    private Long activeTime;
    @TableField("department")
    private Integer userDepartmentId;
    @TableField(exist = false)
    private String userDepartmentName;
    @TableField("role")
    private Integer userRoleId;
    @TableField(exist = false)
    private String userRoleName;
    @TableField(exist = false)
    private Integer privilegeLevel;
}
