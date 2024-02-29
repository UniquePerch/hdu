package com.hdu.hdufpga.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.entity.constant.FileConstant;
import com.hdu.hdufpga.entity.po.PaperPO;
import com.hdu.hdufpga.entity.vo.PaperVO;
import com.hdu.hdufpga.mapper.PaperMapper;
import com.hdu.hdufpga.service.PaperService;
import com.hdu.util.ConvertUtil;
import com.hdu.util.MFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;


@Service
@Slf4j
public class PaperServiceImpl extends MPJBaseServiceImpl<PaperMapper, PaperPO> implements PaperService {
    @Resource
    PaperMapper paperMapper;

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
}
