package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@TableName("t_problem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Problem1PO extends BaseEntity {
    private Integer type;
    private String content;
    private String choice;
    private String answer;
}
