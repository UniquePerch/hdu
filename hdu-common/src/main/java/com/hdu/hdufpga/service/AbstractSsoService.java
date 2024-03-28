package com.hdu.hdufpga.service;

/**
 * 子系统SSO抽象类
 */
public abstract class AbstractSsoService {

    /**
     * 子系统登录
     *
     * @param username 用户名
     * @return 登录后Session信息
     */
    public abstract Object login(String username);

    /**
     * 子系统注销
     *
     * @param username 用户名
     */
    public abstract void logout(String username);

    /**
     * 子系统识别码
     *
     * @return 识别码
     */
    public abstract String type();

}
