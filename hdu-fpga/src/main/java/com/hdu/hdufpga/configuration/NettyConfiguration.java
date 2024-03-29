package com.hdu.hdufpga.configuration;

import com.hdu.hdufpga.netty.FpgaNettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Configuration
@Slf4j
public class NettyConfiguration {
    @Resource
    @Qualifier("fpgaServerInitializer")
    FpgaNettyServerInitializer fpgaNettyServerInitializer;

    @Value("${netty.boss.thread.count}")
    private int bossCount;

    @Value("${netty.worker.thread.count}")
    private int workerCount;

    @Value("${netty.port}")
    private int tcpPort;

    @Value("${netty.keepalive}")
    private boolean keepAlive;

    @Value("${netty.backlog}")
    private int backlog;


    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b
                .group(bossGroup(), workerGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(fpgaNettyServerInitializer)
                .option(ChannelOption.SO_BACKLOG, backlog)
                .option(ChannelOption.SO_KEEPALIVE, keepAlive)
                .option(ChannelOption.SO_REUSEADDR, true)
        ;
        return b;
    }


    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public EventLoopGroup bossGroup() {
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup() {
        return new NioEventLoopGroup(workerCount);
    }

    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(tcpPort);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
