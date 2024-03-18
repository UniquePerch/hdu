package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("t_visit_record")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class VisitRecordPO {
    String sessionId;
    Date visitTime;
}
