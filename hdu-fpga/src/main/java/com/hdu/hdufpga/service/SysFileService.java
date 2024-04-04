package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.SysFilePO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SysFileService extends MPJBaseService<SysFilePO> {
    Boolean uploadBit(HttpServletRequest request, MultipartFile file) throws IOException;


    Boolean reloadBitFile(String token) throws FileNotFoundException;
}
