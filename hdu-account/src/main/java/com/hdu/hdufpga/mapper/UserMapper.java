package com.hdu.hdufpga.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hdu.entity.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}