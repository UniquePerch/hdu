package com.hdu.hdufpga.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PaperVO {
    String title;
    String link;
    Integer classId;
    Integer createByUserId;
    MultipartFile file;
    Date deadline;
}
