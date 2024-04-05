package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.constant.CircuitBoardConstant;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.exception.CircuitBoardException;
import com.hdu.hdufpga.mapper.CircuitBoardMapper;
import com.hdu.hdufpga.service.CbUseRecordService;
import com.hdu.hdufpga.service.CircuitBoardHistoryOperationService;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.util.RedissionLockUtil;
import com.hdu.hdufpga.utils.CircuitBoardUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class CircuitBoardServiceImpl extends MPJBaseServiceImpl<CircuitBoardMapper, CircuitBoardPO> implements CircuitBoardService {
    @Resource
    RedissionLockUtil redissionLockUtil;

    @Resource
    CbUseRecordService cbUseRecordService;

    @Resource
    CircuitBoardHistoryOperationService circuitBoardHistoryOperationService;

    @Resource
    RedisUtil redisUtil;

    @Value("${wait-queue.name}")
    String queueName;

    @Override
    public CircuitBoardPO getAFreeCircuitBoard() throws CircuitBoardException {
        redissionLockUtil.lock("allocateCircuitBoard");
        try {
            LambdaQueryWrapper<CircuitBoardPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CircuitBoardPO::getStatus, false);
            queryWrapper.eq(CircuitBoardPO::getIsReserved, false);
            queryWrapper.eq(CircuitBoardPO::getIsRecorded, false);
            List<CircuitBoardPO> circuitBoardPOS = baseMapper.selectList(queryWrapper);
            if (!circuitBoardPOS.isEmpty()) {
                CircuitBoardPO circuitBoardPO = circuitBoardPOS.get(RandomUtil.randomInt(circuitBoardPOS.size()));
                circuitBoardPO.setStatus(true);
                baseMapper.updateById(circuitBoardPO);
                return circuitBoardPO;
            } else {
                throw new CircuitBoardException("暂无空闲板卡");
            }
        } finally {
            redissionLockUtil.unlock("allocateCircuitBoard");
        }
    }

    @Override
    public CircuitBoardPO getCircuitBoardByIp(String ip) {
        LambdaQueryWrapper<CircuitBoardPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CircuitBoardPO::getIp, ip);
        List<CircuitBoardPO> list = baseMapper.selectList(wrapper);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public String freeCircuitBoard(String cbIp) {
        final String reg = "^(\\d+\\.){3}\\d+:\\d+$";
        if (ReUtil.isMatch(reg, cbIp)) {
            LambdaQueryWrapper<CircuitBoardPO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CircuitBoardPO::getIp, cbIp);
            List<CircuitBoardPO> circuitBoardPOList = baseMapper.selectList(wrapper);
            if (!circuitBoardPOList.isEmpty()) {
                circuitBoardPOList.forEach(e -> {
                    ChannelHandlerContext ctx = redisUtil.getCtx(RedisConstant.REDIS_HOLDER + e.getLongId());
                    CircuitBoardUtil.sendEndToCB(ctx);
                    HashMap<String, Object> CBInfo = redisUtil.getHash(RedisConstant.REDIS_HOLDER + e.getLongId());
                    if (Validator.isNull(CBInfo)) CBInfo = new HashMap<>();
                    CBInfo.put(CircuitBoardConstant.STATUS, false);
                    CBInfo.put(CircuitBoardConstant.IS_RECORDED, false);
                    CBInfo.remove(CircuitBoardConstant.FILE_PATH);
                    CBInfo.remove(CircuitBoardConstant.COUNT);
                    CBInfo.put(CircuitBoardConstant.BUTTON_STATUS, "");
                    CBInfo.put(CircuitBoardConstant.LIGHT_STATUS, "");
                    redisUtil.putHash(RedisConstant.REDIS_HOLDER + e.getLongId(), CBInfo);
                    e.setStatus(false);
                    baseMapper.updateById(e);
                });
            }
            return cbIp;
        } else {
            return null;
        }
    }

    @Override
    public Long getFreeCircuitBoardCount() {
        LambdaQueryWrapper<CircuitBoardPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CircuitBoardPO::getStatus, false);
        queryWrapper.eq(CircuitBoardPO::getIsReserved, false);
        queryWrapper.eq(CircuitBoardPO::getIsRecorded, false);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public CircuitBoardPO getByCBLongId(String longId) {
        LambdaQueryWrapper<CircuitBoardPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CircuitBoardPO::getLongId, longId);
        List<CircuitBoardPO> list = baseMapper.selectList(wrapper);
        if (!list.isEmpty()) return list.get(0);
        else return null;
    }

    @Override
    public Integer updateByLongId(CircuitBoardPO circuitBoardPO) {
        LambdaUpdateWrapper<CircuitBoardPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper
                .eq(CircuitBoardPO::getLongId, circuitBoardPO.getLongId());
        return baseMapper.update(circuitBoardPO, wrapper);
    }

    @Override
    public void deleteByLongId(String longId) {
        LambdaUpdateWrapper<CircuitBoardPO> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CircuitBoardPO::getLongId, longId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void recordBitToBitForTheFirstTime(String token, String filePath) {
        UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
        String longId = userConnectionVO.getLongId();
        if (Validator.isNotNull(longId) && !longId.isEmpty()) {
            ChannelHandlerContext ctx = redisUtil.getCtx(RedisConstant.REDIS_HOLDER + longId);
            HashMap<String, Object> info = redisUtil.getHash(RedisConstant.REDIS_HOLDER + longId);
            info.put(CircuitBoardConstant.IS_RECORDED, false);
            info.put(CircuitBoardConstant.COUNT, 0);
            info.put(CircuitBoardConstant.FILE_PATH, filePath);
            redisUtil.putHash(RedisConstant.REDIS_HOLDER + longId, info);
            log.info("instance: {}", redisUtil.getHash(RedisConstant.REDIS_HOLDER + longId));
            CircuitBoardUtil.recordBitToCB(ctx, filePath, 0);
        }
    }

    @Override
    public Boolean clearUserRedisAndFreeCB(String token) throws CircuitBoardException, SQLException {
        UserConnectionVO connectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
        if (Validator.isNotNull(connectionVO)) {
            if (cbUseRecordService.saveUseRecord(connectionVO)) {
                if (freeCircuitBoard(connectionVO.getCbIp()) == null) {
                    throw new CircuitBoardException("释放板卡失败");
                }
                redisUtil.del(RedisConstant.REDIS_CONN_PREFIX + token, RedisConstant.REDIS_TTL_PREFIX + token, RedisConstant.REDIS_CONN_SHADOW_PREFIX + token);
                redisUtil.removeInZSet(queueName, token);
                log.info("用户{}使用信息已保存，redis记录已清除", token);
                circuitBoardHistoryOperationService.clearSteps(token);
                log.info("用户{}相关文件已清除", token);
            } else {
                throw new SQLException("保存用户信息错误");
            }
        }
        return false;
    }

    @Override
    public Boolean getRecordStatus(String token, String cbIp) throws CircuitBoardException {
        CircuitBoardPO circuitBoardPO = getCircuitBoardByIp(cbIp);
        if (Validator.isNotNull(circuitBoardPO)) {
            Boolean isRecorded = (Boolean) redisUtil.getHashValue(RedisConstant.REDIS_HOLDER + circuitBoardPO.getLongId(), CircuitBoardConstant.IS_RECORDED);
            if (Validator.isNotNull(isRecorded)) {
                return isRecorded;
            } else {
                throw new CircuitBoardException("isRecorded为空");
            }
        } else {
            throw new CircuitBoardException("找不到这个板卡");
        }
    }

    @Override
    public Boolean sendButtonString(String token, String switchButtonStatus, String tapButtonStatus) throws CircuitBoardException {
        UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
        String longId = userConnectionVO.getLongId();
        String finalString = CircuitBoardUtil.processButtonString(switchButtonStatus, tapButtonStatus);
        redisUtil.putHashValue(RedisConstant.REDIS_HOLDER + longId, CircuitBoardConstant.BUTTON_STATUS, finalString);
        Boolean isRecorded = (Boolean) redisUtil.getHashValue(RedisConstant.REDIS_HOLDER + longId, CircuitBoardConstant.IS_RECORDED);
        if (Validator.isNotNull(isRecorded) && isRecorded) {
            CircuitBoardUtil.sendButtonStringToCB(redisUtil.getCtx(longId), finalString);
            circuitBoardHistoryOperationService.saveOperationStep(token, finalString);
            return true;
        } else {
            throw new CircuitBoardException("板卡未烧录");
        }
    }

    @Override
    public String getLightString(String token) throws CircuitBoardException {
        UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
        String longId = userConnectionVO.getLongId();
        String lightString = (String) redisUtil.getHashValue(RedisConstant.REDIS_HOLDER + longId, CircuitBoardConstant.LIGHT_STATUS);
        if (Validator.isNotNull(lightString)) {
            return lightString;
        } else {
            throw new CircuitBoardException("LED灯状态为空");
        }
    }

    @Override
    public String getNixieTubeString(String token) throws CircuitBoardException {
        UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
        String longId = userConnectionVO.getLongId();
        String nixieTubeString = (String) redisUtil.getHashValue(RedisConstant.REDIS_HOLDER + longId, CircuitBoardConstant.NIXIE_TUBE_STATUS);
        if (Validator.isNotNull(nixieTubeString)) {
            return nixieTubeString;
        } else {
            throw new CircuitBoardException("数码管状态为空");
        }
    }

    @Override
    public String getProcessedBtnStr(String token) throws CircuitBoardException {
        UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
        String longId = userConnectionVO.getLongId();
        String buttonString = (String) redisUtil.getHashValue(RedisConstant.REDIS_HOLDER + longId, CircuitBoardConstant.BUTTON_STATUS);
        if (Validator.isNotNull(buttonString)) {
            return buttonString;
        } else {
            throw new CircuitBoardException("按钮状态状态为空");
        }
    }
}
