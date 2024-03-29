package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
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
    public CircuitBoardPO getAFreeCircuitBoard() {
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
                return null;
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
                log.info("版卡{}已经释放!", e.getLongId());
            });
            return longId;
        } else {
            return null;
        }
    }
}
