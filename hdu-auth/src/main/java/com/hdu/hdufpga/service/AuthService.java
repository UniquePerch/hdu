package com.hdu.hdufpga.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.hdu.hdufpga.entity.po.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AuthService {
    @Resource
    private List<AbstractSsoService> ssoServices;

    @DubboReference
    private UserService userService;

    /**
     * SSO登录
     *
     * @param username        用户名
     * @param password        密码
     * @param applicationName 子系统 application.name
     * @return 子系统的登录信息
     */
    public Object login(String username, String password, String applicationName) {
        // 如果已经登录则直接返回
        if (StpUtil.isLogin(username)) {
            for (AbstractSsoService service : ssoServices) {
                if (StrUtil.equals(service.type(), applicationName)) {
                    return service.login(username);
                }
            }
            return null;
        }
        UserPO userPO = userService.getUserByUserName(username);
        if (Objects.isNull(userPO)) {
            return null;
        }
        if (!StrUtil.equals(password, userPO.getPassword())) {
            return null;
        }
        Object result = null;
        for (AbstractSsoService service : ssoServices) {
            if (StrUtil.equals(service.type(), applicationName)) {
                result = service.login(username);
                break;
            }
        }
        if (Objects.nonNull(result)) {
            StpUtil.login(username);
            return result;
        }
        return null;
    }

    /**
     * 退出登录
     *
     * @param username 用户名
     */
    public void logout(String username) {
        StpUtil.logout(username);
        for (AbstractSsoService service : ssoServices) {
            service.logout(username);
        }
    }
}
