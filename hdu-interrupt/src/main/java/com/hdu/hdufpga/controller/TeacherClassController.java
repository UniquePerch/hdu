package com.hdu.hdufpga.controller;

import com.hdu.entity.Result;
import com.hdu.entity.vo.UserVO;
import com.hdu.hdufpga.service.TeacherClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/co/class")
@Slf4j
public class TeacherClassController {
    @Resource
    TeacherClassService teacherClassService;
    @PostMapping(value = "/importStudentFromExcel",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result importStudentFromExcel(MultipartFile file, Integer classId, Integer departmentId) {
        try {
            List<UserVO> userVOList = teacherClassService.uploadStudentList(file, classId, departmentId);
            if(userVOList.isEmpty()){
                return Result.ok();
            } else {
                return Result.error("以下学生导入失败:"+userVOList);
            }
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }
}
