package com.hdu.hdufpga.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.hdu.hdufpga.entity.po.ClassPO;
import com.hdu.hdufpga.entity.po.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassMapper extends MPJBaseMapper<ClassPO> {
    int insertStudentClassRelation(@Param("student_id") Integer studentId, @Param("class_id") Integer classId);

    List<UserPO> selectStudentListByClassId(Integer classId);
}
