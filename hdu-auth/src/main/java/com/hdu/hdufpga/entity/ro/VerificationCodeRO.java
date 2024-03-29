package com.hdu.hdufpga.entity.ro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class VerificationCodeRO {
    /**
     * 宽度
     */
    Integer width = 200;
    /**
     * 长度
     */
    Integer height = 50;

    /**
     * 干扰线长度
     */
    Integer thickness = 4;

    /**
     * 验证码长度
     */
    Integer numberLength = 1;
}
