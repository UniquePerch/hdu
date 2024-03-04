package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@TableName("t_user_test_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TestRecordPO extends BaseEntity {
    private Integer userId;
    private Integer classId;
    private Double score;
    @TableField("problems")
    private String problemsJson;
    @TableField("choices")
    private String choicesJson;
}
