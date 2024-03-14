package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@TableName("t_knowledge")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KnowledgePO extends BaseEntity {
    @TableField("knowledge")
    private String knowledgeContent;
}
