package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.ChapterPO;
import com.hdu.hdufpga.entity.vo.UserChapterRecordVO;

import java.util.List;

public interface ChapterService extends MPJBaseService<ChapterPO> {
    Boolean recordFinish(Integer userId, Integer chapterId);

    List<UserChapterRecordVO> getAllChapterRecord();
}
