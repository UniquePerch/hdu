package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.ClassPO;
import com.hdu.hdufpga.entity.vo.ClassVO;
import com.hdu.hdufpga.entity.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClassService extends MPJBaseService<ClassPO> {
    List<UserVO> uploadStudentList(MultipartFile file, Integer classId, Integer departmentId) throws IOException;

    List<ClassVO> getSortedClassList(Integer teacherId);

    List<UserVO> getStudentListByClassId(Integer classId);
}
