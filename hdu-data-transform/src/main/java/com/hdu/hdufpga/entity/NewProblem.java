package com.hdu.hdufpga.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_problem")
public class NewProblem {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer type;
    private String content;
    private String choice;
    private String answer;
    @TableLogic
    private String isDeleted;

    private Date createTime;
    private Date updateTime;
}
