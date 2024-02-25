package com.hdu.hdufpga.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.mapper.UserMapper;
import com.hdu.hdufpga.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
}
