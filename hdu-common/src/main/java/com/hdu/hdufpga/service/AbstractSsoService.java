package com.hdu.hdufpga.service;

import cn.dev33.satoken.stp.StpUtil;

/**
 * 子系统SSO抽象类
 */
public interface AbstractSsoService {


    /**
     * 子系统登录
     *
     * @param username 用户名
     * @return 登录后Session信息
     */
    default Object login(String username) {
        System.out.println("login username " + username);
        if (!StpUtil.isLogin(username)) {
            StpUtil.login(username);
        }
        return StpUtil.getSessionByLoginId(username);
    }

    /**
     * 子系统注销
     *
     * @param username 用户名
     */
    default void logout(String username) {
        System.out.println("logout username " + username);
        StpUtil.logout(username);
    }

    /**
     * 子系统识别码
     *
     * @return 识别码
     */
    String getApplicationName();

}
