package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.PaperPO;
import com.hdu.hdufpga.entity.vo.PaperVO;

import java.io.IOException;
import java.util.List;

public interface PaperService extends MPJBaseService<PaperPO> {
    Boolean uploadPaper(PaperVO paperVO) throws IOException;

    List<PaperVO> getPapersByClassId(Integer classId);
}
