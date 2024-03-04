package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hdu.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@TableName("t_problem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem1PO extends BaseEntity implements Comparable<Problem1PO> {
    private Integer type;
    private String content;
    private String choice;
    private String answer;


    @Override
    public int compareTo(Problem1PO o) {
        if(this.id < o.id) return -1;
        else if(this.id.equals(o.id)) return 0;
        else return 1;
    }
}
