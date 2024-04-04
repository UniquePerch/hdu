package com.hdu.hdufpga.netty;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.hdu.hdufpga.entity.constant.CircuitBoardConstant;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.util.RedissionLockUtil;
import com.hdu.hdufpga.utils.CircuitBoardUtil;
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
    RedissionLockUtil lock;
    @Resource
    CircuitBoardService circuitBoardService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("电路板消息:{}", msg);
        String ip = (ctx.channel().remoteAddress().toString().split("/"))[1];
        String longId = msg.split("#")[1];
        lock.lock(longId);
        try {
            if (msg.contains("Login")) {
                processLoginReq(ctx, ip, longId);
            }
            if (msg.contains("Heartbeat")) {
                processHeartBeatReq(ctx, ip, longId);
            }
            if (msg.contains("OK")) {
                processOKReq(ctx, longId);
            }
            if (msg.contains("END")) {
                processENDReq(longId);
            }
            if (msg.contains("bye")) {
                processBYEReq();
            }
            if (msg.contains("NICE")) {
                processNICEReq();
            }
            if (msg.contains("STAT")) {
                processSTATReq(ip, msg);
            }
        } finally {
            lock.unlock(longId);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        String ip = (ctx.channel().remoteAddress().toString().split("/"))[1];
        log.info("电路板 : {} 状态：active !", ip);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        String ip = (ctx.channel().remoteAddress().toString().split("/"))[1];
        CircuitBoardPO circuitBoard = circuitBoardService.getCircuitBoardByIp(ip);
        if (Validator.isNotNull(circuitBoard)) {
            String longId = circuitBoard.getLongId();
            redisUtil.removeHash(RedisConstant.REDIS_HOLDER + longId);
            redisUtil.del(RedisConstant.REDIS_BOARD_SERVER_PREFIX + longId);
            circuitBoardService.deleteByLongId(longId);
            log.error("板卡:{}，失去连接，已经从缓存与数据库中移除", longId);
        }
        ctx.channel().close();
        log.error("Channel is disconnected");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error(cause.toString());
    }

    private void processSTATReq(String ip, String msg) {
        String longId = circuitBoardService.getCircuitBoardByIp(ip).getLongId();
        String[] stats = msg.split("STAT");
        String[] str = stats[1].split("#");
        // 旧板子
        if (str[1].length() == 8) {
            String lightString = str[1];
            log.info("lightString:{}", lightString);
            redisUtil.putHashValue(RedisConstant.REDIS_HOLDER + longId, CircuitBoardConstant.LIGHT_STATUS, lightString);
            redisUtil.putHashValue(RedisConstant.REDIS_HOLDER, CircuitBoardConstant.NIXIE_TUBE_STATUS, "");
        } else if (str[1].length() == 18) {
            String lightString = str[1].substring(0, 8);
            String nixieTubeString = StrUtil.reverse(str[1].substring(8, 16));
            log.info("lightString:{}, nixieTubeString:{}", lightString, nixieTubeString);
            redisUtil.putHashValue(RedisConstant.REDIS_HOLDER + longId, CircuitBoardConstant.LIGHT_STATUS, lightString);
            redisUtil.putHashValue(RedisConstant.REDIS_HOLDER, CircuitBoardConstant.NIXIE_TUBE_STATUS, nixieTubeString);
        } else {
            log.error("STAT数据格式错误");
        }
        log.info("已更新light状态! ID:{}, status:{}", longId, str[1]);
    }

    private void processNICEReq() {
        log.info("nice!");
    }

    private void processBYEReq() {
        log.info("bye!");
    }

    private void processENDReq(String londId) {
        redisUtil.set(RedisConstant.REDIS_BOARD_SERVER_PREFIX + londId, true, RedisConstant.REDIS_BOARD_SERVER_LIMIT, TimeUnit.SECONDS);
        redisUtil.putHashValue(RedisConstant.REDIS_HOLDER + londId, CircuitBoardConstant.IS_RECORDED, true);
        log.info("已更新烧录状态！longId:{}", londId);
    }

    private void processLoginReq(ChannelHandlerContext ctx, String ip, String longId) {
        redisUtil.set(RedisConstant.REDIS_BOARD_SERVER_PREFIX + longId, true, RedisConstant.REDIS_BOARD_SERVER_LIMIT, TimeUnit.SECONDS);
        if (longId.matches(pattern) && longId.length() == 4) {
            insertNewCBToMap(ctx, longId, ip);
            insertNewCBToDB(longId, ip);
        } else {
            log.error("电路板longId为 {} 格式不对，被拒绝加入map和DB", longId);
        }
    }

    private void processOKReq(ChannelHandlerContext ctx, String longId) {
        redisUtil.set(RedisConstant.REDIS_BOARD_SERVER_PREFIX + longId, true, RedisConstant.REDIS_BOARD_SERVER_LIMIT, TimeUnit.SECONDS);
        HashMap<String, Object> info = redisUtil.getHash(RedisConstant.REDIS_HOLDER + longId);
        Integer count = (Integer) info.get(CircuitBoardConstant.COUNT);
        String filepath = (String) info.get(CircuitBoardConstant.FILE_PATH);
        CircuitBoardUtil.recordBitToCB(ctx, filepath, count + 1);
        redisUtil.putHashValue(RedisConstant.REDIS_HOLDER + longId, CircuitBoardConstant.COUNT, count + 1);
    }

    private void processHeartBeatReq(ChannelHandlerContext ctx, String ip, String longId) {
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
        if (Validator.isNull(redisUtil.getHash(RedisConstant.REDIS_HOLDER + longdId))) {
            HashMap<String, Object> info = initMap(ctx, ip, longdId);
            redisUtil.putHash(RedisConstant.REDIS_HOLDER + longdId, info);
            log.info("添加新板卡到 map！ longId: {} remoteAddress : {}", longdId, ip);
        }
    }

    private void insertNewCBToMap(ChannelHandlerContext ctx, String longId, String ip) {
        HashMap<String, Object> info = initMap(ctx, ip, longId);
        if (Validator.isNotNull(redisUtil.getHash(RedisConstant.REDIS_HOLDER + longId))) {
            log.info("缓存中已经缓存板卡信息");
        } else {
            log.info("添加新的板卡到map中 longId:{},remoteAddress:{}", longId, ip);
        }
        redisUtil.putHash(RedisConstant.REDIS_HOLDER + longId, info);
    }

    private void insertNewCBToDB(String longId, String ip) {
        CircuitBoardPO circuitBoardPO = circuitBoardService.getByCBLongId(longId);
        if (Validator.isNotNull(circuitBoardPO)) {
            updateCBObjectWithLongId(longId, ip, circuitBoardPO);
            Integer res = circuitBoardService.updateByLongId(circuitBoardPO);
            log.info("cb数据库更新:{},结果:{}", longId, res > 0);
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

    private HashMap<String, Object> initMap(ChannelHandlerContext ctx, String IP, String longId) {
        HashMap<String, Object> info = new HashMap<>();
        info.put(CircuitBoardConstant.IP, IP);
        info.put(CircuitBoardConstant.CTX, ctx);
        info.put(CircuitBoardConstant.STATUS, "0");
        info.put(CircuitBoardConstant.IS_RECORDED, "0");
        info.put(CircuitBoardConstant.BUTTON_STATUS, "");
        info.put(CircuitBoardConstant.LIGHT_STATUS, "");
        info.put(CircuitBoardConstant.NIXIE_TUBE_STATUS, "");
        info.put(CircuitBoardConstant.LONG_ID, longId);
        return info;
    }
}
