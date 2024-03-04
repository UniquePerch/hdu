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
public class TestRecordVO extends BaseEntity {
    private Integer userId;
    private Integer classId;
    private Double score;
    private String problemsJson;
    private String choicesJson;
}
