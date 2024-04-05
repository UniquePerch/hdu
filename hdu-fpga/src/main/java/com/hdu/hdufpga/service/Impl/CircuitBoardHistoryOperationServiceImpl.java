package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.exception.EmptyHistoryStepsException;
import com.hdu.hdufpga.service.CircuitBoardHistoryOperationService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.utils.CircuitBoardUtil;
import com.hdu.hdufpga.utils.SysFileUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CircuitBoardHistoryOperationServiceImpl implements CircuitBoardHistoryOperationService {
    @Resource
    RedisUtil redisUtil;

    @Override
    public void clearSteps(String token) {
        FileUtil.del(SysFileUtil.getFullPath(token));
        FileUtil.del(SysFileUtil.getFullBitFilePath(token));
        log.info("已清空用户{}历史操作记录", token);
    }


    @Override
    public Boolean loadOperationHistory(String token) throws EmptyHistoryStepsException {
        UserConnectionVO connectionVo = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
        String longId = connectionVo.getLongId();
        ChannelHandlerContext ctx = redisUtil.getCtx(longId);
        List<String> steps = readSteps(token);
        if (Validator.isNotEmpty(steps) && !steps.isEmpty()) {
            for (String step : steps) {
                redisUtil.set(RedisConstant.REDIS_TTL_PREFIX + token, true, RedisConstant.REDIS_TTL_LIMIT, TimeUnit.SECONDS);
                CircuitBoardUtil.sendButtonStringToCB(ctx, step);
            }
            return true;
        } else {
            throw new EmptyHistoryStepsException("历史记录为空");
        }
    }

    @Override
    public void saveOperationStep(String token, String step) {
        String fullPath = SysFileUtil.getFullPath(token);
        FileUtil.appendUtf8String(step + "\n", fullPath);
    }

    private List<String> readSteps(String token) {
        String fullPath = SysFileUtil.getFullPath(token);
        return FileUtil.readUtf8Lines(fullPath);
    }
}
