package com.hdu.hdufpga.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Qualifier("fpgaServerInitializer")
public class FpgaNettyServerInitializer extends ChannelInitializer<SocketChannel> {
    StringEncoder stringEncoder = new StringEncoder();

    StringDecoder stringDecoder = new StringDecoder();

    @Resource
    FpgaNettyServerHandler fpgaNettyServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("decoder", stringDecoder);
        channelPipeline.addLast("handler", fpgaNettyServerHandler);
        channelPipeline.addLast("encoder", stringEncoder);
    }
}
