package com.hdu.hdufpga.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Component
@Slf4j
public class FpgaNettyServer {
    @Resource
    ServerBootstrap serverBootstrap;

    @Resource
    InetSocketAddress inetSocketAddress;

    private ChannelFuture channelFuture;

    @PostConstruct
    public void start() {
        try {
            channelFuture = serverBootstrap.bind(inetSocketAddress).sync();
            log.info("监听端口{}成功", inetSocketAddress);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("监听端口{}失败", inetSocketAddress);
        }
    }

    @PreDestroy
    public void stop() {
        channelFuture.channel().closeFuture();
        log.info("关闭端口{}成功", inetSocketAddress);
    }
}
