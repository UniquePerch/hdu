package com.hdu.hdufpga.service;

import com.hdu.entity.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeacherClassService {
    List<UserVO> uploadStudentList(MultipartFile file, Integer classId, Integer departmentId) throws IOException;
}
