package com.hdu.hdufpga.service.impl;


import cn.hutool.core.convert.Convert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.hdu.entity.po.UserPO;
import com.hdu.entity.vo.UserVO;
import com.hdu.hdufpga.client.UserClient;
import com.hdu.hdufpga.mapper.TeacherClassMapper;
import com.hdu.hdufpga.service.TeacherClassService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherClassServiceImpl implements TeacherClassService {
    @Resource
    UserClient userClient;

    @Resource
    TeacherClassMapper teacherClassMapper;

    //todo:分布式事务
    @Override
    @GlobalTransactional
    public List<UserVO> uploadStudentList(MultipartFile file, Integer classId, Integer departmentId) throws IOException {
        List<UserVO> voList = new ArrayList<>();
        EasyExcel
                .read()
                .file(file.getInputStream())
                .head(UserVO.class)
                .registerReadListener(new PageReadListener<UserVO>(voList::addAll))
                .sheet()
                .doRead();
        voList.forEach(userVO -> {
            userVO.setUserDepartmentId(departmentId);
            userVO.setPassword("123456");
            userVO.setUserRoleId(1);
        });
        List<UserPO> poList = new ArrayList<>();
        voList.forEach(e->poList.add(Convert.convert(UserPO.class,e)));
        voList.clear();
        poList.forEach(e->{
            if(userClient.create(e).getResult()==null){
                voList.add(Convert.convert(UserVO.class,e));
            } else {
                //TODO:要先查询才能知道用户id，这里直接getId是不行的
                teacherClassMapper.insertStudentClassRelation(e.getId(),classId);
            }
        });
        return voList;
    }
}
