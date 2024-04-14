package com.hdu.hdufpga.netty;

import com.hdu.hdufpga.entity.constant.CircuitBoardConstant;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

//线程安全的懒汉模式
public class NettySocketHolder {

    private static final HashMap<String, HashMap<String, Object>> nettySocketHolder = new HashMap<>(100);

    /**
     * 将信息存入map
     */
    public static void put(String longId, HashMap<String, Object> info) {
        nettySocketHolder.put(longId, info);
    }

    public static void putValue(String longId, String key, Object value) {
        nettySocketHolder.get(longId).put(key, value);
    }

    /**
     * 从map中替换
     */
    public static void replace(String longId, HashMap<String, Object> info) {
        nettySocketHolder.replace(longId, info);
    }

    /**
     * 从map中删除
     */
    public static void remove(String longId) {
        nettySocketHolder.remove(longId);
    }

    /**
     * 返回 info
     */
    public static HashMap<String, Object> getInfo(String longId) {
        return nettySocketHolder.get(longId);
    }

    public static Object getValue(String longId, String key) {
        return nettySocketHolder.get(longId).get(key);
    }


    /**
     * 返回 ctx
     */
    public static ChannelHandlerContext getCtx(String longId) {
        return (ChannelHandlerContext) NettySocketHolder.getInfo(longId).get(CircuitBoardConstant.CTX);
    }
}