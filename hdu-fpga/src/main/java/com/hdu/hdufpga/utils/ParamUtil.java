package com.hdu.hdufpga.utils;

import com.hdu.hdufpga.entity.vo.UserVO;
import com.mysql.cj.util.StringUtils;

public class ParamUtil {
    public static Boolean CheckUserInfoLegal(UserVO userVO) {
        return !StringUtils.isNullOrEmpty(userVO.getUsername()) && !StringUtils.isNullOrEmpty(userVO.getUserDepartmentName()) && !StringUtils.isNullOrEmpty(userVO.getRealName());
    }

    public static String generateUserToken(UserVO userVO, String salt) {
        if (!StringUtils.isNullOrEmpty(salt)) {
            return String.join("_", userVO.getUsername(), userVO.getRealName(), userVO.getUserDepartmentName(), salt);
        } else {
            throw new IllegalArgumentException("salt为空,请重新生成");
        }
    }
}
