package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("t_sys_file")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysFilePO extends BaseEntity {
    private String name;
    private String originalName;
    private String type;
    private String info;
    private String absolutePath;
    private String resourcePath;
}
