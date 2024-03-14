package com.hdu.hdufpga.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
