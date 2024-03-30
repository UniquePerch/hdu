package com.hdu.hdufpga.entity.constant;

public class RedisConstant {
    //保持连接 身份->true
    public final static String REDIS_TTL_PREFIX = "ttl:";
    public final static Integer REDIS_TTL_LIMIT = 3 * 60; //connection must be checked within 3 minutes
    public final static String REDIS_CONN_PREFIX = "conn:";
    //板卡使用计时器的shadow,单纯的倒计时器
    public final static String REDIS_CONN_SHADOW_PREFIX = "shadow:";
    public final static Integer REDIS_CONN_SHADOW_LIMIT = 30 * 60;

    public final static String REDIS_SESSION_PREFIX = "session:";

    public final static String REDIS_LOCK_PREFIX = "lock:";
    //板卡连接服务器的倒计时
    public final static String REDIS_BOARD_SERVER_PREFIX = "boardConnection:";
    public final static Integer REDIS_BOARD_SERVER_LIMIT = 2 * 60 + 30;

    public final static String REDIS_HOLDER = "holder:";

}
