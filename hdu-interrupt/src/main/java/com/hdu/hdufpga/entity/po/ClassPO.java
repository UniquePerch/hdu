package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.entity.BaseEntity;
import lombok.*;


@EqualsAndHashCode(callSuper = false)
@TableName("t_class")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassPO extends BaseEntity {
    String name;
    Boolean isOver;

    @TableField("create_by")
    Integer createByUserId;

    String createByUsername;
}
