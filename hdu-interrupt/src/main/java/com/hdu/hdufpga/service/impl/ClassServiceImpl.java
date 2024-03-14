package com.hdu.hdufpga.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.constant.AccountConstant;
import com.hdu.hdufpga.entity.constant.RoleConstant;
import com.hdu.hdufpga.entity.po.ClassPO;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.entity.vo.ClassVO;
import com.hdu.hdufpga.entity.vo.UserVO;
import com.hdu.hdufpga.mapper.ClassMapper;
import com.hdu.hdufpga.service.ClassService;
import com.hdu.hdufpga.service.UserService;
import com.hdu.hdufpga.util.ConvertUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@DubboService
public class ClassServiceImpl extends MPJBaseServiceImpl<ClassMapper, ClassPO> implements ClassService {
    @DubboReference
    UserService userService;

    @Resource
    ClassMapper classMapper;

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
            if(!userService.save(e)){
                voList.add(ConvertUtil.copy(e,UserVO.class));
                poList.remove(e);
            }
        });
        List<String> userNameList = new ArrayList<>();
        poList.forEach(e->userNameList.add(e.getUsername()));
        List<Integer> idList = userService.getIdByUserName(userNameList);
        idList.forEach(e-> classMapper.insertStudentClassRelation(e,classId));
        return voList;
    }

    @Override
    public List<ClassVO> getSortedClassList(Integer teacherId) {
        LambdaQueryWrapper<ClassPO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(ClassPO::getCreateByUserId,teacherId)
                .orderByDesc(ClassPO::getCreateTime)
        ;
        List<ClassPO> poList = classMapper.selectList(wrapper);
        return ConvertUtil.copyList(poList,ClassVO.class);
    }

    @Override
    public List<UserVO> getStudentListByClassId(Integer classId) {
        List<UserPO> poList = classMapper.selectStudentListByClassId(classId);
        return ConvertUtil.copyList(poList,UserVO.class);
    }
}
