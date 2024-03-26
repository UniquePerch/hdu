package com.hdu.hdufpga.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem1VO extends BaseEntity implements Comparable<Problem1VO> {
    private Integer type;
    private String content;
    private String choice;
    private String answer;

    @Override
    public int compareTo(Problem1VO o) {
        if (this.id < o.id) return -1;
        else if (this.id.equals(o.id)) return 0;
        else return 1;
    }
}
