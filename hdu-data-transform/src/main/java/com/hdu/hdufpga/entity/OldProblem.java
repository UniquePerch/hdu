package com.hdu.hdufpga.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("co_test_problem")
public class OldProblem {
    @TableId(type = IdType.AUTO)
    private Integer problemId;
    private Integer type;
    private String content;
    private String choice;
    private String answer;
}
