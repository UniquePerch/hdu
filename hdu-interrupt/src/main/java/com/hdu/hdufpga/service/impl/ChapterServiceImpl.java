package com.hdu.hdufpga.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hdu.hdufpga.entity.PageRecord;
import com.hdu.hdufpga.entity.po.ChapterPO;
import com.hdu.hdufpga.entity.po.UserChapterRecordPO;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.entity.vo.UserChapterRecordVO;
import com.hdu.hdufpga.mapper.ChapterMapper;
import com.hdu.hdufpga.mapper.UserChapterMapper;
import com.hdu.hdufpga.service.ChapterService;
import com.hdu.hdufpga.util.ConvertUtil;
import com.hdu.hdufpga.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterServiceImpl extends MPJBaseServiceImpl<ChapterMapper, ChapterPO> implements ChapterService {
    @Resource
    ChapterMapper chapterMapper;

    @Resource
    UserChapterMapper userChapterMapper;

    @Override
    public Boolean recordFinish(Integer userId, Integer chapterId) {
        return chapterMapper.recordFinish(userId, chapterId, TimeUtil.getNowTime()) == 1;
    }

    @Override
    public PageRecord<UserChapterRecordVO> getAllChapterRecord(Integer current, Integer size) {
        MPJLambdaWrapper<UserChapterRecordPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .select(UserChapterRecordPO::getId)
                .selectAs(UserPO::getId, UserChapterRecordPO::getUserId)
                .selectAs(UserPO::getUsername, UserChapterRecordPO::getUserName)
                .selectAs(UserPO::getRealName, UserChapterRecordPO::getRealName)
                .selectAs(ChapterPO::getId, UserChapterRecordPO::getChapterId)
                .selectAs(ChapterPO::getTitle, UserChapterRecordPO::getChapterTitle)
                .select(UserChapterRecordPO::getFinishTime)
                .leftJoin(UserPO.class, UserPO::getId, UserChapterRecordPO::getUserId)
                .leftJoin(ChapterPO.class, ChapterPO::getId, UserChapterRecordPO::getChapterId)
        ;
        Page<UserChapterRecordPO> poPage = userChapterMapper.selectJoinPage(new Page<>(current, size), UserChapterRecordPO.class, wrapper);
        PageRecord<UserChapterRecordVO> voPage = new PageRecord<>();
        voPage.setTotal(poPage.getTotal());
        voPage.setCurrent(poPage.getCurrent());
        voPage.setSize(poPage.getSize());
        voPage.setObject(ConvertUtil.copyList(poPage.getRecords(), UserChapterRecordVO.class));
        return voPage;
    }

    @Override
    public List<UserChapterRecordVO> getChapterRecordByUserId(Integer userId) {
        MPJLambdaWrapper<UserChapterRecordPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .selectAs(UserPO::getId, UserChapterRecordPO::getUserId)
                .selectAs(UserPO::getUsername, UserChapterRecordPO::getUserName)
                .selectAs(ChapterPO::getId, UserChapterRecordPO::getChapterId)
                .selectAs(UserPO::getRealName, UserChapterRecordPO::getRealName)
                .selectAs(ChapterPO::getTitle, UserChapterRecordPO::getChapterTitle)
                .leftJoin(UserPO.class, UserPO::getId, UserChapterRecordPO::getUserId)
                .leftJoin(ChapterPO.class, ChapterPO::getId, UserChapterRecordPO::getChapterId)
                .eq(UserChapterRecordPO::getUserId, userId)
        ;
        return ConvertUtil.copyList(userChapterMapper.selectJoinList(UserChapterRecordPO.class, wrapper), UserChapterRecordVO.class);
    }
}
