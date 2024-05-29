package com.hdu.hdufpga.service.Impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.hdu.hdufpga.entity.constant.SysConstant;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class SaTokenImpl implements StpInterface {

    @Resource
    private UserService userService;

    /**
     * 获取权限集合
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        log.info("获取用户权限集合列表");
        return Collections.emptyList();
    }

    /**
     * 获取用户角色
     *
     * @param loginId   账号id
     * @param loginType 账号类型
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        log.info("获取角色列表");
        if (StrUtil.isBlankIfStr(loginId)) {
            log.warn("获取到的login id 为空 {}", loginId);
            return Collections.emptyList();
        }
        String loginName = loginId.toString();
        String[] usernameAndDepartmentIdList = loginName.split(SysConstant.DASH);
        if (usernameAndDepartmentIdList.length != 2 || !NumberUtil.isNumber(usernameAndDepartmentIdList[SysConstant.ONE])) {
            log.warn("获取到的登录账号格式错误 {}", loginName);
            return Collections.emptyList();
        }
        String username = usernameAndDepartmentIdList[SysConstant.ZERO];
        Integer departmentId = Integer.valueOf(usernameAndDepartmentIdList[SysConstant.ONE]);
        UserPO userPO = userService.getUserByUserName(username, departmentId);
        if (Objects.isNull(userPO)) {
            log.warn("根据用户名 {} 学校id {} 查询的用户为空", username, departmentId);
            return Collections.emptyList();
        }
        return Lists.newArrayList(String.valueOf(userPO.getUserRoleId()));
    }
}
