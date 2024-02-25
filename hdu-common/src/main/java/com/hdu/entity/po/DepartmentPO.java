package com.hdu.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@TableName("t_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentPO extends BaseEntity {
    private String name;
    private Integer fatherDepartment;
}
