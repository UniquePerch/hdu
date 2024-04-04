package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.io.FileUtil;
import com.hdu.hdufpga.service.CircuitBoardHistoryOperationService;
import com.hdu.hdufpga.utils.SysFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CircuitBoardHistoryOperationServiceImpl implements CircuitBoardHistoryOperationService {
    @Override
    public void clearSteps(String token) {
        FileUtil.del(SysFileUtil.getFullPath(token));
        FileUtil.del(SysFileUtil.getFullBitFilePath(token));
        log.info("已清空用户{}历史操作记录", token);
    }
}
