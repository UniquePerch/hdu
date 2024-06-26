package com.hdu.hdufpga.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaperVO extends BaseEntity {
    String title; //标题
    String link; // 报告链接
    Integer classId; //班级Id
    Integer createByUserId; //创建者Id
    MultipartFile file; //文件
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date deadline; //截止日期
}
