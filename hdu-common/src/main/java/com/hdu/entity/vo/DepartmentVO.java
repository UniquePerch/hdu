package com.hdu.entity.vo;

import com.hdu.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DepartmentVO extends BaseEntity {
    private String name;
    private Integer fatherDepartment;
}
