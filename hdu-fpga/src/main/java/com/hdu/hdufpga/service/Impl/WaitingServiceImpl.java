package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import cn.hutool.extra.servlet.ServletUtil;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.service.WaitingService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.util.RedissionLockUtil;
import com.hdu.hdufpga.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WaitingServiceImpl implements WaitingService {
    @Resource
    RedisUtil redisUtil;

    @Resource
    RedissionLockUtil lockUtil;

    @Resource
    CircuitBoardService circuitBoardService;

    @Value("${wait-queue.name}")
    String queueName;

    @Resource
    HttpServletRequest request;

    @Override
    public Result userInQueue(String token) {
        lockUtil.lock(queueName);
        try {
            if (Validator.isNull(getRankInQueue(token))) {
                redisUtil.multi();
                boolean bShadow = redisUtil.set(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token, true);
                UserConnectionVO userConnectionVO = createUserConnectionVO(token);
                boolean bWaiting = redisUtil.set(RedisConstant.REDIS_CONN_PREFIX + token, userConnectionVO);
                redisUtil.set(RedisConstant.REDIS_TTL_PREFIX + token, true);
                redisUtil.addInZSet(queueName, token);
                redisUtil.exec();
                if (bShadow && bWaiting) {
                    return Result.ok(userConnectionVO);
                } else {
                    return Result.error("用户验证出错");
                }
            } else {
                return Result.error("已经在队列中");
            }
        } catch (Exception e) {
            redisUtil.discard();
            log.error(e.getMessage());
            return Result.error("出错了");
        } finally {
            lockUtil.unlock(queueName);
        }
    }

    @Override
    public Result checkAvailability(String token) {
        Long number = getRankInQueue(token);
        if (Validator.isNull(number)) {
            return Result.error("用户不在队列中");
        } else if (number == 0) {
            CircuitBoardPO circuitBoardPO = circuitBoardService.getAFreeCircuitBoard();
            if (Validator.isNotNull(circuitBoardPO)) {
                UserConnectionVO userConnectionVO = unfreezeConnection(token, circuitBoardPO);
                if (userConnectionVO == null) {
                    return Result.error("解冻用户失败,请重试");
                }
                return Result.ok(userConnectionVO);
            }
        } else {
            return Result.ok("用户在队列中，排在第" + number + "位");
        }
        return Result.error("出错了,请稍后重试");
    }

    @Override
    public boolean freezeConnection(String token) {
        lockUtil.lock(token);
        try {
            Object shadow = redisUtil.get(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token);
            UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
            if (Validator.isNotNull(shadow) && Validator.isNotNull(userConnectionVO) && !userConnectionVO.getIsFrozen()) {
                userConnectionVO.setIsFrozen(true);
                userConnectionVO.setLeftSecond(redisUtil.getExpire(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token, TimeUnit.SECONDS));
                userConnectionVO.setUpdateDate(TimeUtil.getNowTime());
                redisUtil.multi();
                redisUtil.set(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token, true, -1, TimeUnit.SECONDS);
                redisUtil.set(RedisConstant.REDIS_CONN_PREFIX + token, userConnectionVO, -1, TimeUnit.SECONDS);
                redisUtil.exec();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        } finally {
            lockUtil.unlock(token);
        }
    }

    private Long getRankInQueue(String token) {
        return redisUtil.getZSetRank(queueName, token);
    }

    private UserConnectionVO getConnectionVOInRedis(String token) {
        if (!redisUtil.hasKey(RedisConstant.REDIS_CONN_PREFIX + token)) {
            return null;
        }
        Object o = redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token);
        return Convert.convert(UserConnectionVO.class, o);
    }

    private UserConnectionVO createUserConnectionVO(String token) {
        UserConnectionVO userConnectionVO = getConnectionVOInRedis(token);
        if (Validator.isNull(userConnectionVO)) {
            userConnectionVO = new UserConnectionVO();
            userConnectionVO.setUserIp(ServletUtil.getClientIP(request));
            userConnectionVO.setIsFrozen(true);
            userConnectionVO.setCreateDate(TimeUtil.getNowTime());
            userConnectionVO.setUpdateDate(TimeUtil.getNowTime());
            userConnectionVO.setLeftSecond(RedisConstant.REDIS_CONN_SHADOW_LIMIT.longValue());
            userConnectionVO.setToken(token);
        }
        return userConnectionVO;
    }

    private UserConnectionVO unfreezeConnection(String token, CircuitBoardPO circuitBoard) {
        lockUtil.lock(token);
        try {
            UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX) + token);
            Object shadow = redisUtil.get(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token);
            if (Validator.isNotNull(shadow) && Validator.isNotNull(userConnectionVO) && userConnectionVO.getIsFrozen()) {
                redisUtil.removeInZSet(queueName, token);
                userConnectionVO.setIsFrozen(false);
                userConnectionVO.setCbIp(circuitBoard.getIp());
                userConnectionVO.setLongId(circuitBoard.getLongId());
                userConnectionVO.setUpdateDate(TimeUtil.getNowTime());
                redisUtil.multi();
                redisUtil.set(RedisConstant.REDIS_CONN_PREFIX + token, userConnectionVO);
                redisUtil.set(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token, true, userConnectionVO.getLeftSecond(), TimeUnit.SECONDS);
                redisUtil.expire(RedisConstant.REDIS_TTL_PREFIX + token, RedisConstant.REDIS_TTL_LIMIT, TimeUnit.SECONDS);
                redisUtil.exec();
                return userConnectionVO;
            } else {
                return null;
            }
        } catch (Exception e) {
            redisUtil.discard();
            log.error(e.getMessage());
            return null;
        } finally {
            lockUtil.unlock(token);
        }
    }
}
