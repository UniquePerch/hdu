package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.ClassPO;
import com.hdu.hdufpga.entity.vo.UserVO;
import com.hdu.hdufpga.service.ClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/class")
@Slf4j
public class ClassController extends BaseController<ClassService, ClassPO> {
    //level >= 2
    @Override
    public Result create(ClassPO classPO) {
        return super.create(classPO);
    }

    //level >= 2
    @Override
    public Result update(ClassPO classPO) {
        return super.update(classPO);
    }

    //level >= 2
    @Override
    public Result delete(ClassPO classPO) {
        return super.delete(classPO);
    }

    //level >= 3
    @Override
    public Result listPage(Integer current, Integer size) {
        return super.listPage(current, size);
    }

    //level >= 3
    @Override
    public Result get(Integer id) {
        return super.get(id);
    }

    //level >= 2
    @RequestMapping(value = "/importStudentFromExcel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result importStudentFromExcel(MultipartFile file, Integer classId, Integer departmentId) {
        try {
            List<UserVO> userVOList = service.uploadStudentList(file, classId, departmentId);
            if (userVOList.isEmpty()) {
                return Result.ok();
            } else {
                return Result.error("以下学生导入失败:" + userVOList);
            }
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    //level >= 2
    @RequestMapping("/getSortedClassListByTeacherId")
    public Result getSortedClassListByTeacherId(Integer teacherId) {
        try {
            return Result.ok(service.getSortedClassList(teacherId));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    //level >= 2
    @RequestMapping("/getStudentListByClassId")
    public Result getStudentListByClassId(Integer classId) {
        try {
            return Result.ok(service.getStudentListByClassId(classId));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }
}
