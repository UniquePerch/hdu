package com.hdu.hdufpga.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.hdu.hdufpga.entity.po.ChapterPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface ChapterMapper extends MPJBaseMapper<ChapterPO> {

    int recordFinish(@Param("user_id") Integer userId, @Param("chapter_id") Integer chapterId, @Param("finish_time") Date now);
}
