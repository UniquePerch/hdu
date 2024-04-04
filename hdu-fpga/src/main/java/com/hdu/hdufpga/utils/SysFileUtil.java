package com.hdu.hdufpga.utils;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.io.FileExistsException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class SysFileUtil {
    public static void saveFile(MultipartFile multipartFile, String fullPath) throws IOException {
        if (multipartFile.isEmpty()) return;
        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new FileExistsException("创建文件夹出错");
            }
        }
        multipartFile.transferTo(file);
    }

    public static String getBase() {
        String base = "hdu/operation/";
        String absolutePath = FileUtil.getAbsolutePath(base);
        if (absolutePath.contains("target/")) return "../../" + base;
        else return base;
    }

    public static String getFullPath(String fileName) {
        String opFileSuffix = ".txt";
        return FileUtil.getAbsolutePath(getBase() + fileName + opFileSuffix);
    }

    public static String getBitFileBase() {
        String base = "hdu/upload/";
        String absolutePath = FileUtil.getAbsolutePath(base);
        if (absolutePath.contains("target/")) return "../../" + base;
        else return base;
    }

    public static String getFullBitFilePath(String fileName) {
        String bitFileSuffix = ".bit";
        return FileUtil.getAbsolutePath(getBitFileBase() + fileName + bitFileSuffix);
    }
}
