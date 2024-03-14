package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.UserPO;

import java.util.List;


public interface UserService extends MPJBaseService<UserPO> {
    List<Integer> getIdByUserName(List<String> poList);
}
