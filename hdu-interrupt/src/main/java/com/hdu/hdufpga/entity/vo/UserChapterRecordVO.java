package com.hdu.hdufpga.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserChapterRecordVO {
    Integer id;
    Integer userId;
    Integer chapterId;
    Date finishTime;
    String userName;
    String realName;
    Integer mark;
    String chapterTitle;
}
