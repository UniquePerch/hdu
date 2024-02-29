package com.hdu.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.UnicodeUtil;
import com.hdu.entity.constant.FileConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

@Slf4j
public class MFileUtil {
    public static String uploadFile(MultipartFile file, String basePath) throws IOException {
        String absoluteFilePath = basePath + getDatePath() + file.getOriginalFilename();
        absoluteFilePath = UnicodeUtil.toUnicode(absoluteFilePath);
        if(file.getSize() <= 0){
            log.error("空文件！");
            throw new IllegalArgumentException("空文件！");
        }
        if(Objects.isNull(file.getOriginalFilename())){
            log.error("文件名为空");
            throw new IllegalArgumentException("文件名为空");
        }
        if(file.getOriginalFilename().length() > FileConstant.MAX_FILENAME_LENGTH){
            log.error("文件名过长");
            throw new IllegalArgumentException("文件名过长");
        }
        File dest = new File(absoluteFilePath);
        if(FileUtil.exist(dest)){
            log.info("文件{}已存在，正在删除文件", dest.getAbsoluteFile());
            FileUtil.del(dest);
        }
        dest = FileUtil.touch(dest);
        FileUtil.writeFromStream(file.getInputStream(), dest);
        return FileUtil.getAbsolutePath(dest);
    }

    public static File downloadFile(String filePath) {
        if(!FileUtil.exist(filePath)) {
            log.error("文件{}不存在", filePath);
            throw new IllegalArgumentException("文件不存在");
        }
        return FileUtil.file(filePath);
    }

    public static String getDatePath(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/";
    }
}
