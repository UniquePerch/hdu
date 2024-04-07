package com.hdu.hdufpga.service.Impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hdu.hdufpga.entity.po.DepartmentPO;
import com.hdu.hdufpga.entity.po.RolePO;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.mapper.UserMapper;
import com.hdu.hdufpga.service.UserService;
import com.hdu.hdufpga.util.TimeUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@DubboService
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, UserPO> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public List<Integer> getIdByUserName(List<String> userNameList, Integer departmentId) {
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(UserPO::getId)
                .in(UserPO::getUsername, userNameList)
                .eq(UserPO::getUserDepartmentId, departmentId);
        List<UserPO> poList = userMapper.selectList(wrapper);
        List<Integer> idList = new ArrayList<>();
        poList.forEach(e -> idList.add(e.getId()));
        return idList;
    }

    @Override
    public Long getUserCountByDate(Date startDate, Date endDate) {
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(UserPO::getCreateTime, startDate, endDate);
        return userMapper.selectCount(wrapper);
    }

    @Override
    public boolean save(UserPO entity) {
        entity.setCreateTime(TimeUtil.getNowTime());
        entity.setUpdateTime(TimeUtil.getNowTime());
        entity.setPassword(SecureUtil.md5(entity.getPassword()));
        return super.save(entity);
    }

    @Override
    public boolean updateById(UserPO entity) {
        if (entity.getPassword() != null) {
            entity.setPassword(SecureUtil.md5(entity.getPassword()));
        }
        return super.updateById(entity);
    }

    @Override
    public UserPO getUserByUserName(String userName, Integer departmentId) {
        MPJLambdaWrapper<UserPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .selectAll(UserPO.class)
                .selectAs(RolePO::getPrivilegeCharacter, UserPO::getUserRoleName)
                .selectAs(RolePO::getPrivilegeLevel, UserPO::getPrivilegeLevel)
                .selectAs(DepartmentPO::getName, UserPO::getUserDepartmentName)
                .leftJoin(RolePO.class, RolePO::getId, UserPO::getUserRoleId)
                .leftJoin(DepartmentPO.class, DepartmentPO::getId, UserPO::getUserDepartmentId)
                .eq(UserPO::getUsername, userName)
                .eq(UserPO::getUserDepartmentId, departmentId)
        ;
        return userMapper.selectJoinOne(UserPO.class, wrapper);
    }
}
