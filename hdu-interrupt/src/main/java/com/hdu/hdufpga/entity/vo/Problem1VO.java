package com.hdu.hdufpga.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hdu.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem1VO extends BaseEntity {
    private Integer type;
    private String content;
    private String choice;
    private String answer;
}
