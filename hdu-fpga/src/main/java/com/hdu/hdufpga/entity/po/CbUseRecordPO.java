package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@TableName("t_cb_use_record")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CbUseRecordPO extends BaseEntity {
    //使用的LongId;
    private String cbId;

    private String cbIp;

    private String userName;

    private String userIp;

    @TableField("school_name")
    private String departmentName;

    private Integer duration;

    private Integer fileUploadTime;

}
