package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import cn.hutool.extra.servlet.ServletUtil;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.exception.UserQueueException;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.service.WaitingService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.util.RedissionLockUtil;
import com.hdu.hdufpga.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public UserConnectionVO userInQueue(String token) throws UserQueueException {
        lockUtil.lock(queueName);
        try {
            if (Validator.isNull(getRankInQueue(token))) {
                //如果有空闲的板子，那就不入队，直接找一块
                if (circuitBoardService.getFreeCircuitBoardCount() > 0) {
                    UserConnectionVO userConnectionVO = createUserConnectionVO(token);
                    boolean bShadow = redisUtil.set(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token, true, 12, TimeUnit.HOURS);
                    boolean bWaiting = redisUtil.set(RedisConstant.REDIS_CONN_PREFIX + token, userConnectionVO, 12, TimeUnit.HOURS);
                    if (bWaiting && bShadow) {
                        CircuitBoardPO circuitBoardPO = circuitBoardService.getAFreeCircuitBoard();
                        if (Validator.isNotNull(circuitBoardPO)) {
                            userConnectionVO = unfreezeConnection(token, circuitBoardPO);
                            if (Validator.isNull(userConnectionVO)) {
                                throw new UserQueueException("解冻用户失败");
                            } else {
                                return userConnectionVO;
                            }
                        } else {
                            throw new UserQueueException("分配板卡失败");
                        }
                    } else {
                        throw new UserQueueException("用户验证出错");
                    }
                }
            } else {
                throw new UserQueueException("已经在队列中");
            }

            if (Validator.isNull(getRankInQueue(token))) {
                boolean bShadow = redisUtil.set(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token, true, 12, TimeUnit.HOURS);
                UserConnectionVO userConnectionVO = createUserConnectionVO(token);
                boolean bWaiting = redisUtil.set(RedisConstant.REDIS_CONN_PREFIX + token, userConnectionVO, 12, TimeUnit.HOURS);
                redisUtil.addInZSet(queueName, token);
                if (bShadow && bWaiting) {
                    return userConnectionVO;
                } else {
                    throw new UserQueueException("用户验证出错");
                }
            } else {
                throw new UserQueueException("已经在队列中");
            }
        } finally {
            lockUtil.unlock(queueName);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result checkAvailability(String token) throws UserQueueException {
        Long number = getRankInQueue(token);
        if (Validator.isNull(number)) {
            throw new UserQueueException("用户不在队列中");
        } else if (number == 0) {
            CircuitBoardPO circuitBoardPO = circuitBoardService.getAFreeCircuitBoard();
            if (Validator.isNotNull(circuitBoardPO)) {
                redisUtil.removeInZSet(queueName, token);
                UserConnectionVO userConnectionVO = unfreezeConnection(token, circuitBoardPO);
                if (userConnectionVO == null) {
                    throw new UserQueueException("解冻用户失败,请重试");
                }
                return Result.ok(userConnectionVO);
            } else {
                throw new UserQueueException("分配板卡失败");
            }
        } else {
            return Result.ok("用户在队列中，排在第" + number + "位");
        }
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
                redisUtil.set(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token, true, 12, TimeUnit.HOURS);
                redisUtil.set(RedisConstant.REDIS_CONN_PREFIX + token, userConnectionVO, 12, TimeUnit.HOURS);
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
            UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
            Object shadow = redisUtil.get(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token);
            if (Validator.isNotNull(shadow) && Validator.isNotNull(userConnectionVO) && userConnectionVO.getIsFrozen()) {
                userConnectionVO.setIsFrozen(false);
                userConnectionVO.setCbIp(circuitBoard.getIp());
                userConnectionVO.setLongId(circuitBoard.getLongId());
                userConnectionVO.setUpdateDate(TimeUtil.getNowTime());
                redisUtil.set(RedisConstant.REDIS_CONN_PREFIX + token, userConnectionVO);
                redisUtil.set(RedisConstant.REDIS_CONN_SHADOW_PREFIX + token, true, userConnectionVO.getLeftSecond(), TimeUnit.SECONDS);
                return userConnectionVO;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        } finally {
            lockUtil.unlock(token);
        }
    }
}