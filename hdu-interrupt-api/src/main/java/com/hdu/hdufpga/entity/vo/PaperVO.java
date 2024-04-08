package com.hdu.hdufpga.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class PaperVO {
    String title;
    String link;
    Integer classId;
    Integer createByUserId;
    MultipartFile file;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date deadline;
}
