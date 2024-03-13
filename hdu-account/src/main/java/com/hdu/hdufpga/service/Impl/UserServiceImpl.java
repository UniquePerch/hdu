package com.hdu.hdufpga.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.mapper.UserMapper;
import com.hdu.hdufpga.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, UserPO> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public List<Integer> getIdByUserName(List<String> userNameList) {
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(UserPO::getId)
                .in(UserPO::getUsername, userNameList);
        List<UserPO> poList = userMapper.selectList(wrapper);
        List<Integer> idList = new ArrayList<>();
        poList.forEach(e->idList.add(e.getId()));
        return idList;
    }
}
