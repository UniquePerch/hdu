package com.hdu.hdufpga.service.Impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.entity.po.UserPO;
import com.hdu.hdufpga.mapper.UserMapper;
import com.hdu.hdufpga.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, UserPO> implements UserService {
}
