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
public class LoginRO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 学校编号
     */
    private Integer departmentId;

    /**
     * 验证码key
     */
    private String verificationCodeKey;

    /**
     * 验证码value
     */
    private String verificationCodeValue;

    /**
     * 应用名称:application.name
     */
    private String applicationName;

}
