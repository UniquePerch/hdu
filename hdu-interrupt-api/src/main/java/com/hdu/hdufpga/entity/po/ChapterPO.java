package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@TableName("t_chapter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterPO extends BaseEntity {
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
