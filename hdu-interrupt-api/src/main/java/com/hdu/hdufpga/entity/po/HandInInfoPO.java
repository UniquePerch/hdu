package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@TableName("foreign_user_paper")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandInInfoPO extends BaseEntity {
    Integer paperId;
    Integer classId;
    Integer userId;
    @TableField("link")
    String filePath;
    Boolean state;
    Double grade;
    String userRealName;
    String userName;
}
