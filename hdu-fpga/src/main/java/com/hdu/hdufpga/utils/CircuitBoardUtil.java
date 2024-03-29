package com.hdu.hdufpga.utils;

import io.netty.channel.ChannelHandlerContext;

public class CircuitBoardUtil {
    public static void sendEndToCB(ChannelHandlerContext ctx) {
        ctx.channel().writeAndFlush("NNN");
    }
}
