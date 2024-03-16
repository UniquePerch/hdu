package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@TableName("t_resource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourcePO extends BaseEntity {
    String resourceName;
}
