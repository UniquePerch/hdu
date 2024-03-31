package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.exception.CircuitBoardException;
import com.hdu.hdufpga.mapper.CircuitBoardMapper;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.util.RedissionLockUtil;
import com.hdu.hdufpga.utils.CircuitBoardUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class CircuitBoardServiceImpl extends MPJBaseServiceImpl<CircuitBoardMapper, CircuitBoardPO> implements CircuitBoardService {
    @Resource
    RedissionLockUtil redissionLockUtil;

    @Resource
    RedisUtil redisUtil;

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
    public String freeCircuitBoard(String longId) {
        LambdaQueryWrapper<CircuitBoardPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CircuitBoardPO::getLongId, longId);
        List<CircuitBoardPO> circuitBoardPOList = baseMapper.selectList(wrapper);
        if (!circuitBoardPOList.isEmpty()) {
            circuitBoardPOList.forEach(e -> {
                ChannelHandlerContext ctx = redisUtil.getCtx(e.getLongId());
                CircuitBoardUtil.sendEndToCB(ctx);
                HashMap<String, Object> CBInfo = redisUtil.getHash(e.getLongId());
                if (Validator.isNull(CBInfo)) CBInfo = new HashMap<>();
                CBInfo.put("statud", "0");
                CBInfo.put("isRecorded", "0");
                CBInfo.remove("filePath");
                CBInfo.remove("count");
                CBInfo.put("buttonStatus", "");
                CBInfo.put("lightStatus", "");
                redisUtil.putHash(e.getLongId(), CBInfo);
                e.setStatus(false);
                baseMapper.updateById(e);
            });
            return longId;
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
}
