package com.hdu.hdufpga.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterVO extends BaseEntity {
    String intro;
    String process;
    String videoPath;
    String pptPath;
    String animatePath;
    String linkFilePath;
    Integer mark;
    Integer number;
    String title;
}
