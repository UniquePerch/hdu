package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.PaperPO;
import com.hdu.hdufpga.entity.vo.HandInInfoVO;
import com.hdu.hdufpga.entity.vo.PaperVO;
import com.hdu.hdufpga.exception.HomeworkException;

import java.io.IOException;
import java.util.List;

public interface PaperService extends MPJBaseService<PaperPO> {
    Boolean uploadPaper(PaperVO paperVO) throws IOException;

    List<PaperVO> getPapersByClassId(Integer classId);

    Boolean handInPaper(HandInInfoVO handInInfoVO) throws IOException, HomeworkException;

    List<HandInInfoVO> getHandInInfoByClassId(Integer classId);

    Boolean correctingPaper(HandInInfoVO handInInfoVO);

    Boolean returnPaper(Integer id);

    Boolean updateHandInInfo(HandInInfoVO handInInfoVO) throws HomeworkException, IOException;

    List<HandInInfoVO> getHandInInfoByUserId(Integer userId);

    Boolean deletePaper(PaperVO paperVO) throws Exception;
}
