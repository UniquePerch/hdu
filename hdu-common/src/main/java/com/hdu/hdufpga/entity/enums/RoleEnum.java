package com.hdu.hdufpga.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    STUDENT("1", "学生"),
    TEACHER("2", "教师"),
    ADMIN("3", "管理员");

    private final String roleId;

    private final String roleName;
}
