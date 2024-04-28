package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.io.FileUtil;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.SysFilePO;
import com.hdu.hdufpga.exception.InvalidFileSuffixException;
import com.hdu.hdufpga.mapper.SysFileMapper;
import com.hdu.hdufpga.service.CircuitBoardHistoryOperationService;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.service.SysFileService;
import com.hdu.hdufpga.util.TimeUtil;
import com.hdu.hdufpga.utils.SysFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@Slf4j
public class SysFileServiceImpl extends MPJBaseServiceImpl<SysFileMapper, SysFilePO> implements SysFileService {
    @Resource
    CircuitBoardService circuitBoardService;

    @Resource
    CircuitBoardHistoryOperationService circuitBoardHistoryOperationService;


    @Override
    @Transactional
    public Boolean uploadBit(HttpServletRequest request, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IOException("文件为空");
        }
        String bitPattern = ".*?\\.bit$";
        // 进行文件名校验，确认上传的是后缀为bit的文件
        if (originalFileName.matches(bitPattern)) {
            String token = request.getHeader("token");
            String filePath = SysFileUtil.getFullBitFilePath(token);
            // 先删除文件
            FileUtil.del(filePath);
            // 清楚历史步骤，表示重新开始实验
            circuitBoardHistoryOperationService.clearSteps(token);
            // 保存文件
            SysFileUtil.saveFile(file, filePath);
            SysFilePO sysFilePO = new SysFilePO();
            sysFilePO.setType("bit");
            sysFilePO.setCreateTime(TimeUtil.getNowTime());
            sysFilePO.setAbsolutePath(filePath);
            sysFilePO.setOriginalName(originalFileName);
            // 保存数据库记录
            saveOrUpdate(sysFilePO);
            // 烧录板卡
            circuitBoardService.recordBitToBitForTheFirstTime(token, filePath);
            return true;
        } else {
            throw new InvalidFileSuffixException("文件后缀不为bit");
        }
    }

    @Override
    public Boolean reloadBitFile(String token) throws FileNotFoundException {
        String filePath = SysFileUtil.getFullBitFilePath(token);
        if (FileUtil.exist(filePath)) {
            circuitBoardService.recordBitToBitForTheFirstTime(token, filePath);
            return true;
        } else {
            throw new FileNotFoundException("找不到服务器上的bit文件");
        }
    }
}
