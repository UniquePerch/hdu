package com.hdu.hdufpga.utils;

import com.hdu.hdufpga.entity.constant.CircuitBoardConstant;
import com.hdu.hdufpga.util.MFileUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CircuitBoardUtil {
    public static void recordBitToCB(ChannelHandlerContext ctx, String filePath, Integer count) {
        log.info("文件烧录位置:{}", filePath);
        byte[] b = MFileUtil.fileToBytes(filePath);
        int limit = (int) Math.ceil((double) b.length / CircuitBoardConstant.SLICE_SIZE);
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            int length = CircuitBoardConstant.SLICE_SIZE;
            if (i + 1 >= limit) {
                length = b.length % CircuitBoardConstant.SLICE_SIZE;
            }
            byte[] dest = new byte[CircuitBoardConstant.SLICE_SIZE];
            //截取数组
            System.arraycopy(b, i * CircuitBoardConstant.SLICE_SIZE, dest, 0, length);
            stringList.add(MFileUtil.bytesToHexString(dest)); // 存入链表
        }
        if (count == 0) {
            ctx.channel().writeAndFlush("NNN");
            ctx.channel().writeAndFlush("CALL 1234 #");
            log.info("the first time to send message");
        } else if (count == 1) {
            // b.length 文件字节数  传输前置
            int size = b.length / CircuitBoardConstant.SLICE_SIZE;
            StringBuilder sizeString = new StringBuilder(Integer.toHexString(size));
            for (int i = sizeString.length(); i < 3; i++) {
                sizeString.insert(0, "0");
            }
            int c = b.length * 2;
            StringBuilder sizeString2 = new StringBuilder(Integer.toHexString(c));
            for (int i = sizeString2.length(); i < 6; i++) {
                sizeString2.insert(0, "0");
            }
            sizeString.append(sizeString2);
            ctx.channel().writeAndFlush("SIZE#" + sizeString + "#");
            log.info("发送文件字节大小:{}", sizeString);
        } else if (count < limit + 2) {
            ctx.channel().writeAndFlush("FIL" + "FF" + stringList.get(count - 2));
            log.info("第 " + count + " 次发送数据 " + stringList.get(count - 2).length() * 2);
        } else {
            log.info("烧录完毕");
        }
    }

    public static void sendButtonStringToCB(ChannelHandlerContext ctx, String buttonString) {
        ctx.channel().writeAndFlush("CTR #" + buttonString + "#");
    }

    public static void sendEndToCB(ChannelHandlerContext ctx) {
        ctx.channel().writeAndFlush("NNN");
    }

    public static String processButtonString(String switchButtonStatus, String tapButtonStatus) {
        StringBuilder finalString = new StringBuilder();
        if (switchButtonStatus.length() > 32) {
            log.error("开关状态字符串超长");
        } else if (switchButtonStatus.length() == 32) {
            finalString.append(HexUtil.binaryToHex(switchButtonStatus));
        } else {
            log.error("开关状态字符串缺失:{}", switchButtonStatus.length());
        }

        if (tapButtonStatus.length() == 6) {
            tapButtonStatus = tapButtonStatus + "00";
        }
        if (tapButtonStatus.length() == 8) {
            finalString.append(HexUtil.binaryToHex(tapButtonStatus));
        } else {
            log.error("tapButtonStatus 长度异常: {}", tapButtonStatus.length());
        }
        return finalString.toString();
    }
}
