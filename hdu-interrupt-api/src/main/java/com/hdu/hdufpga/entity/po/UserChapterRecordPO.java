package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@TableName("foreign_user_chapter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChapterRecordPO {
    Integer id;
    Integer userId;
    String userName;
    String realName;
    Integer chapterId;
    Date finishTime;
    Integer mark;
    String chapterTitle;
}
