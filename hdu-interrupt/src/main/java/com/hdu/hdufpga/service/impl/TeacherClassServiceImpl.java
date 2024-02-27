package com.hdu.hdufpga.service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.hdu.entity.constant.AccountConstant;
import com.hdu.entity.constant.RoleConstant;
import com.hdu.entity.po.UserPO;
import com.hdu.entity.vo.UserVO;
import com.hdu.hdufpga.client.UserClient;
import com.hdu.hdufpga.mapper.TeacherClassMapper;
import com.hdu.hdufpga.service.TeacherClassService;
import com.hdu.util.ConvertUtil;
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
            userVO.setPassword(AccountConstant.DEFAULT_PASSWORD);
            userVO.setUserRoleId(RoleConstant.STUDENT);
        });
        List<UserPO> poList = ConvertUtil.copyList(voList, UserPO.class);
        voList.clear();
        poList.forEach(e->{
            if(userClient.create(e).getResult()==null){
                voList.add(ConvertUtil.copy(e,UserVO.class));
                poList.remove(e);
            }
        });
        List<String> userNameList = new ArrayList<>();
        poList.forEach(e->userNameList.add(e.getUsername()));
        List<Integer> idList = userClient.getIdByUserName(userNameList);
        idList.forEach(e-> teacherClassMapper.insertStudentClassRelation(e,classId));
        return voList;
    }
}
