package com.hdu.hdufpga.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hdu.hdufpga.entity.po.ResourcePO;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.entity.po.UserResourceRecordPO;
import com.hdu.hdufpga.entity.vo.UserResourceRecordVO;
import com.hdu.hdufpga.mapper.StudentResourceRecordMapper;
import com.hdu.hdufpga.service.UserResourceRecordService;
import com.hdu.hdufpga.util.ConvertUtil;
import com.hdu.hdufpga.util.TimeUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@DubboService
@Service
public class StudentResourceRecordServiceImpl extends MPJBaseServiceImpl<StudentResourceRecordMapper, UserResourceRecordPO> implements UserResourceRecordService {
    @Resource
    private StudentResourceRecordMapper studentResourceRecordMapper;

    @Override
    public Boolean updateResourceRecord(UserResourceRecordVO userResourceRecordVO) {
        UserResourceRecordPO userResourceRecordPO = ConvertUtil.copy(userResourceRecordVO, UserResourceRecordPO.class);
        Integer res;
        if(checkRecordExist(userResourceRecordPO)) {
            userResourceRecordPO.setUpdateTime(TimeUtil.getNowTime());
            res =  studentResourceRecordMapper.updateUserResourceRecord(userResourceRecordPO);
        } else {
            userResourceRecordPO.setCreateTime(TimeUtil.getNowTime());
            userResourceRecordPO.setUpdateTime(TimeUtil.getNowTime());
            userResourceRecordPO.setTimes(1);
            res = studentResourceRecordMapper.insert(userResourceRecordPO);
        }
        return res > 0;
    }

    @Override
    public List<UserResourceRecordPO> getRecordByUserId(Integer userId) {
        MPJLambdaWrapper<UserResourceRecordPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .selectAs(UserPO::getId,UserResourceRecordPO::getUserId)
                .selectAs(UserPO::getUsername,UserResourceRecordPO::getUserName)
                .selectAs(UserPO::getRealName,UserResourceRecordPO::getUserRealName)
                .selectAs(ResourcePO::getId,UserResourceRecordPO::getResourceId)
                .selectAs(ResourcePO::getResourceName,UserResourceRecordPO::getResourceName)
                .select(UserResourceRecordPO::getDuration)
                .select(UserResourceRecordPO::getTimes)
                .select(UserResourceRecordPO::getCreateTime)
                .select(UserResourceRecordPO::getUpdateTime)
                .leftJoin(UserPO.class,UserPO::getId,UserResourceRecordPO::getUserId)
                .leftJoin(ResourcePO.class,ResourcePO::getId,UserResourceRecordPO::getResourceId)
                .eq(UserResourceRecordPO::getUserId, userId)
                ;
        return studentResourceRecordMapper.selectJoinList(UserResourceRecordPO.class, wrapper);
    }

    private Boolean checkRecordExist(UserResourceRecordPO userResourceRecordPO) {
        LambdaQueryWrapper<UserResourceRecordPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserResourceRecordPO::getUserId, userResourceRecordPO.getUserId())
                .eq(UserResourceRecordPO::getResourceId, userResourceRecordPO.getResourceId());
        return studentResourceRecordMapper.selectCount(queryWrapper) > 0;
    }
}
