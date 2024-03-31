package com.hdu.hdufpga.netty;

import cn.hutool.core.lang.Validator;
import com.hdu.hdufpga.entity.constant.CircuitBoardConstant;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.util.RedisUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
@Qualifier("fpgaServerHandler")
@ChannelHandler.Sharable
@Slf4j
public class FpgaNettyServerHandler extends SimpleChannelInboundHandler<String> {
    private final String pattern = "^[0-9A-Fa-f]{4}$";
    @Resource
    RedisUtil redisUtil;
    @Resource
    CircuitBoardService circuitBoardService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("电路板消息:{}", msg);
        String ip = (ctx.channel().remoteAddress().toString().split("/"))[1];
        if (msg.contains("Login")) {
            processLoginReq(ctx, msg, ip);
        }
        if (msg.contains("Heartbeat")) {
            processHeartBeatReq(ctx, msg, ip);
        }
    }

    private void processHeartBeatReq(ChannelHandlerContext ctx, String msg, String ip) {
        String[] heartbeats = msg.split("Heartbeat");
        String longId = (heartbeats[1].split("#"))[1];
        redisUtil.set(RedisConstant.REDIS_BOARD_SERVER_PREFIX + longId, true, RedisConstant.REDIS_BOARD_SERVER_LIMIT, TimeUnit.SECONDS);
        log.info("心跳包: 来自long_id: " + longId + " ip: " + ip);
        if (longId.matches(pattern) && longId.length() == 4) {
            updateMap(ctx, longId, ip);
            updateDB(longId, ip);
        } else {
            log.error("电路板longId为 {} 格式不对，被拒绝加入map和DB", longId);
        }
    }


    private void updateDB(String longId, String ip) {
        CircuitBoardPO circuitBoardPO = circuitBoardService.getByCBLongId(longId);
        if (circuitBoardPO == null) {
            insertNewCBToDB(longId, ip);
        }
    }

    private void updateMap(ChannelHandlerContext ctx, String longdId, String ip) {
        if (Validator.isNull(redisUtil.getHash(longdId))) {
            HashMap<String, Object> info = initMap(ctx, longdId, ip);
            redisUtil.putHash(longdId, info);
            log.info("添加新板卡到 map！ longId: {} remoteAddress : {}", longdId, ip);
        }
    }

    private void processLoginReq(ChannelHandlerContext ctx, String msg, String ip) {
        String[] logins = msg.split("Login");
        String longId = (logins[1].split("#"))[1];

        redisUtil.set(RedisConstant.REDIS_BOARD_SERVER_PREFIX + longId, true, RedisConstant.REDIS_BOARD_SERVER_LIMIT, TimeUnit.SECONDS);
        if (longId.matches(pattern) && longId.length() == 4) {
            insertNewCBToMap(ctx, longId, ip);
            insertNewCBToDB(longId, ip);
        } else {
            log.error("电路板longId为 {} 格式不对，被拒绝加入map和DB", longId);
        }
    }

    private void insertNewCBToMap(ChannelHandlerContext ctx, String longId, String ip) {
        HashMap<String, Object> info = initMap(ctx, ip, longId);
        if (Validator.isNotNull(redisUtil.getHash(longId))) {
            log.info("缓存中已经缓存板卡信息");
        } else {
            log.info("添加新的板卡到map中 longId:{},remoteAddress:{}", longId, ip);
        }
        redisUtil.putHash(longId, info);
    }

    private void insertNewCBToDB(String longId, String ip) {
        CircuitBoardPO circuitBoardPO = circuitBoardService.getByCBLongId(longId);
        if (Validator.isNotNull(circuitBoardPO)) {
            updateCBObjectWithLongId(longId, ip, circuitBoardPO);
            circuitBoardService.updateByLongId(circuitBoardPO);
            log.info("cb数据库更新:{}", longId);
        } else {
            CircuitBoardPO cb = initCbObject(longId, ip);
            circuitBoardService.save(cb);
            log.info("添加了新的板卡到db中:{}", longId);
        }
    }

    private CircuitBoardPO initCbObject(String longId, String ip) {
        CircuitBoardPO circuitBoardPO = new CircuitBoardPO();
        circuitBoardPO.setIp(ip);
        circuitBoardPO.setLongId(longId);
        circuitBoardPO.setStatus(false);
        circuitBoardPO.setIsReserved(false);
        circuitBoardPO.setIsRecorded(false);
        return circuitBoardPO;
    }

    private void updateCBObjectWithLongId(String longId, String ip, CircuitBoardPO board) {
        board.setLongId(longId);
        board.setIp(ip);
        board.setStatus(false);
        board.setIsRecorded(false);
    }

    private HashMap<String, Object> initMap(ChannelHandlerContext ctx, String IP, String long_id) {
        HashMap<String, Object> info = new HashMap<>();
        info.put(CircuitBoardConstant.IP, IP);
        info.put(CircuitBoardConstant.CTX, ctx);
        info.put(CircuitBoardConstant.STATUS, "0");
        info.put(CircuitBoardConstant.IS_RECORDED, "0");
        info.put(CircuitBoardConstant.BUTTON_STATUS, "");
        info.put(CircuitBoardConstant.LIGHT_STATUS, "");
        info.put(CircuitBoardConstant.NIXIE_TUBE_STATUS, "");
        info.put(CircuitBoardConstant.LONG_ID, long_id);
        return info;
    }
}
