package com.hdu.hdufpga.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HandInInfoVO extends BaseEntity {
    MultipartFile file;
    Integer paperId;
    Integer classId;
    Integer userId;
    String filePath;
    Boolean state;
    Double grade;
    String userRealName;
    String userName;
}
