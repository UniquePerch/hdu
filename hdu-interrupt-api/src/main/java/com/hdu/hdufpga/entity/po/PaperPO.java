package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@TableName("t_paper")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperPO extends BaseEntity {
    String title;
    String link;
    Integer classId;
    @TableField("create_by")
    Integer createByUserId;
    Date deadline;
}
