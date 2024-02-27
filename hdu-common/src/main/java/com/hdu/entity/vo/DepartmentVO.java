package com.hdu.entity.vo;

import com.hdu.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DepartmentVO extends BaseEntity {
    private String name;
    private Integer fatherDepartment;
}
