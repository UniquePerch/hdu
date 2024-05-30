package com.hdu.hdufpga.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hdu.hdufpga.entity.po.VisitRecordPO;
import com.hdu.hdufpga.entity.vo.UserVisitInfoVO;
import com.hdu.hdufpga.mapper.StudentResourceRecordMapper;
import com.hdu.hdufpga.mapper.VisitRecordMapper;
import com.hdu.hdufpga.service.UserRecordService;
import com.hdu.hdufpga.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class StudentRecordServiceImpl implements UserRecordService {
    @Resource
    VisitRecordMapper visitRecordMapper;

    @Resource
    StudentResourceRecordMapper studentResourceRecordMapper;

    @DubboReference(check = false)
    UserService userService;

    @Override
    public UserVisitInfoVO getUserVisitInfo(Date startDate, Date endDate) {
        UserVisitInfoVO userVisitInfoVO = new UserVisitInfoVO();
        LambdaQueryWrapper<VisitRecordPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.between(VisitRecordPO::getVisitTime, startDate, endDate);
        userVisitInfoVO.setVisitCount(visitRecordMapper.selectCount(wrapper));
        userVisitInfoVO.setStudyDuration(studentResourceRecordMapper.getStudnetResourceDurationSum(startDate, endDate));
        userVisitInfoVO.setUserCount(userService.getUserCountByDate(startDate, endDate));
        return userVisitInfoVO;
    }
}
