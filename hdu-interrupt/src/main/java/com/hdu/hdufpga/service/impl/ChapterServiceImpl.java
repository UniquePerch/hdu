package com.hdu.hdufpga.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hdu.entity.po.UserPO;
import com.hdu.hdufpga.entity.po.ChapterPO;
import com.hdu.hdufpga.entity.po.UserChapterRecordPO;
import com.hdu.hdufpga.entity.vo.UserChapterRecordVO;
import com.hdu.hdufpga.mapper.ChapterMapper;
import com.hdu.hdufpga.mapper.UserChapterMapper;
import com.hdu.hdufpga.service.ChapterService;
import com.hdu.util.ConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ChapterServiceImpl extends MPJBaseServiceImpl<ChapterMapper, ChapterPO> implements ChapterService {
    @Resource
    ChapterMapper chapterMapper;

    @Resource
    UserChapterMapper userChapterMapper;
    @Override
    public Boolean recordFinish(Integer userId, Integer chapterId) {
        return chapterMapper.recordFinish(userId,chapterId,new Date()) == 1;
    }

    @Override
    public List<UserChapterRecordVO> getAllChapterRecord() {
        MPJLambdaWrapper<UserChapterRecordPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .select(UserChapterRecordPO::getId)
                .selectAs(UserPO::getId,UserChapterRecordPO::getUserId)
                .selectAs(UserPO::getUsername,UserChapterRecordPO::getUserName)
                .selectAs(UserPO::getRealName,UserChapterRecordPO::getRealName)
                .selectAs(ChapterPO::getId,UserChapterRecordPO::getChapterId)
                .selectAs(ChapterPO::getTitle,UserChapterRecordPO::getChapterTitle)
                .selectAs(UserChapterRecordPO::getMark,UserChapterRecordPO::getMark)
                .select(UserChapterRecordPO::getFinishTime)
                .leftJoin(UserPO.class,UserPO::getId,UserChapterRecordPO::getUserId)
                .leftJoin(ChapterPO.class,ChapterPO::getId,UserChapterRecordPO::getChapterId)
                ;
        return ConvertUtil.copyList(userChapterMapper.selectJoinList(UserChapterRecordPO.class,wrapper),UserChapterRecordVO.class);
    }

    @Override
    public List<UserChapterRecordVO> getChapterRecordByUserId(Integer userId) {
        MPJLambdaWrapper<UserChapterRecordPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .selectAs(UserPO::getId,UserChapterRecordPO::getUserId)
                .selectAs(UserPO::getUsername,UserChapterRecordPO::getUserName)
                .selectAs(ChapterPO::getId,UserChapterRecordPO::getChapterId)
                .selectAs(ChapterPO::getTitle,UserChapterRecordPO::getChapterTitle)
                .leftJoin(UserPO.class,UserPO::getId,UserChapterRecordPO::getUserId)
                .leftJoin(ChapterPO.class,ChapterPO::getId,UserChapterRecordPO::getChapterId)
                .eq(UserChapterRecordPO::getUserId,userId)
                ;
        return ConvertUtil.copyList(userChapterMapper.selectJoinList(UserChapterRecordPO.class,wrapper),UserChapterRecordVO.class);
    }
}
