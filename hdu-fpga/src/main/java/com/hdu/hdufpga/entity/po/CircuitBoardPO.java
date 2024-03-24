package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@TableName("t_cb")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CircuitBoardPO extends BaseEntity {
    private String ip;

    private Boolean status;

    private Boolean isReserved;

    private Boolean isRecorded;

    private String LongId;
}
