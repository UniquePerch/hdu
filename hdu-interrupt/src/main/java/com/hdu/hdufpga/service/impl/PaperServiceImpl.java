package com.hdu.hdufpga.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hdu.hdufpga.entity.constant.FileConstant;
import com.hdu.hdufpga.entity.po.HandInInfoPO;
import com.hdu.hdufpga.entity.po.PaperPO;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.entity.vo.HandInInfoVO;
import com.hdu.hdufpga.entity.vo.PaperVO;
import com.hdu.hdufpga.exception.FileDeleteException;
import com.hdu.hdufpga.exception.HomeworkException;
import com.hdu.hdufpga.mapper.HandInInfoMapper;
import com.hdu.hdufpga.mapper.PaperMapper;
import com.hdu.hdufpga.service.PaperService;
import com.hdu.hdufpga.util.ConvertUtil;
import com.hdu.hdufpga.util.MFileUtil;
import com.hdu.hdufpga.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@Service
@Slf4j
@DubboService
public class PaperServiceImpl extends MPJBaseServiceImpl<PaperMapper, PaperPO> implements PaperService {
    @Resource
    PaperMapper paperMapper;

    @Resource
    HandInInfoMapper handInInfoMapper;

    @Override
    public Boolean uploadPaper(PaperVO paperVO) throws IOException {
        String absoluteFilePath = MFileUtil.uploadFile(paperVO.getFile(), FileConstant.TEACHER_PAPER_UPLOAD_PATH);
        paperVO.setLink(absoluteFilePath);
        PaperPO paperPO = ConvertUtil.copy(paperVO, PaperPO.class);
        return paperMapper.insert(paperPO) > 0;
    }

    @Override
    public List<PaperVO> getPapersByClassId(Integer classId) {
        LambdaQueryWrapper<PaperPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaperPO::getClassId, classId);
        return ConvertUtil.copyList(paperMapper.selectList(wrapper), PaperVO.class);
    }

    @Override
    public Boolean handInPaper(HandInInfoVO handInInfoVO) throws IOException, HomeworkException {
        PaperPO paperPO = paperMapper.selectById(handInInfoVO.getPaperId());
        if (paperPO.getDeadline().before(TimeUtil.getNowTime())) {
            throw new HomeworkException("已超过截止时间");
        }
        handInInfoVO.setFilePath(MFileUtil.uploadFile(handInInfoVO.getFile(), FileConstant.STUDENT_PAPER_UPLOAD_PATH));
        handInInfoVO.setClassId(paperPO.getClassId());
        handInInfoVO.setState(false);
        HandInInfoPO handInInfoPO = ConvertUtil.copy(handInInfoVO, HandInInfoPO.class);
        return handInInfoMapper.insert(handInInfoPO) > 0;
    }

    @Override
    public List<HandInInfoVO> getHandInInfoByClassId(Integer classId) {
        MPJLambdaWrapper<HandInInfoPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .select(HandInInfoPO::getId, HandInInfoPO::getFilePath, HandInInfoPO::getGrade, HandInInfoPO::getState, HandInInfoPO::getPaperId, HandInInfoPO::getUserId, HandInInfoPO::getClassId)
                .selectAs(UserPO::getRealName, HandInInfoPO::getUserRealName)
                .selectAs(UserPO::getUsername, HandInInfoPO::getUserName)
                .leftJoin(UserPO.class, UserPO::getId, HandInInfoPO::getUserId)
                .orderByDesc(UserPO::getUsername)
        ;
        List<HandInInfoPO> poList = handInInfoMapper.selectJoinList(HandInInfoPO.class, wrapper);
        return ConvertUtil.copyList(poList, HandInInfoVO.class);
    }

    @Override
    public Boolean correctingPaper(HandInInfoVO handInInfoVO) {
        LambdaUpdateWrapper<HandInInfoPO> wrapper = new LambdaUpdateWrapper<>();
        HandInInfoPO handInInfoPO = ConvertUtil.copy(handInInfoVO, HandInInfoPO.class);
        wrapper
                .set(HandInInfoPO::getGrade, handInInfoPO.getGrade())
                .set(HandInInfoPO::getState, true)
                .eq(HandInInfoPO::getId, handInInfoPO.getId());
        return handInInfoMapper.update(null, wrapper) > 0;
    }

    @Override
    public Boolean returnPaper(Integer id) {
        return handInInfoMapper.deleteById(id) > 0;
    }

    @Override
    public Boolean updateHandInInfo(HandInInfoVO handInInfoVO) throws HomeworkException, IOException {
        HandInInfoPO handInInfoPOOld = handInInfoMapper.selectById(handInInfoVO.getId());
        PaperPO paperPO = paperMapper.selectById(handInInfoPOOld.getPaperId());
        if (paperPO.getDeadline().before(TimeUtil.getNowTime())) {
            throw new HomeworkException("已超过截止时间");
        }
        if (handInInfoPOOld.getState()) {
            throw new HomeworkException("作业已批改，不可修改提交");
        }
        if (handInInfoVO.getFile() != null && handInInfoVO.getFile().getSize() > 0) {
            handInInfoVO.setFilePath(MFileUtil.uploadFile(handInInfoVO.getFile(), FileConstant.STUDENT_PAPER_UPLOAD_PATH));
        } else {
            throw new HomeworkException("报告为空");
        }
        HandInInfoPO handInInfoPONew = ConvertUtil.copy(handInInfoVO, HandInInfoPO.class);
        return handInInfoMapper.updateById(handInInfoPONew) > 0;
    }

    @Override
    public List<HandInInfoVO> getHandInInfoByUserId(Integer userId) {
        LambdaQueryWrapper<HandInInfoPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(HandInInfoPO::getUserId, userId);
        List<HandInInfoPO> poList = handInInfoMapper.selectList(wrapper);
        return ConvertUtil.copyList(poList, HandInInfoVO.class);
    }

    @Override
    public Boolean deletePaper(PaperVO paperVO) throws Exception {
        PaperPO paperPO = paperMapper.selectById(paperVO.getId());
        if (paperPO == null) {
            throw new Exception("找不到对应的报告");
        }
        if (FileUtil.del(paperPO.getLink())) {
            return paperMapper.deleteById(paperVO.getId()) > 0;
        } else {
            throw new FileDeleteException(paperPO.getLink(), "", "文件删除失败");
        }
    }
}
