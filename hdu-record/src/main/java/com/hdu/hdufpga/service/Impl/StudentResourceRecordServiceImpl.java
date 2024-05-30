package com.hdu.hdufpga.service.Impl;

import cn.hutool.json.JSONUtil;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hdu.hdufpga.entity.po.ResourcePO;
import com.hdu.hdufpga.entity.po.UserPO;
import com.hdu.hdufpga.entity.po.UserResourceRecordPO;
import com.hdu.hdufpga.entity.vo.StudentStudyRecord;
import com.hdu.hdufpga.entity.vo.UserResourceRecordVO;
import com.hdu.hdufpga.entity.vo.UserVO;
import com.hdu.hdufpga.mapper.StudentResourceRecordMapper;
import com.hdu.hdufpga.service.ClassService;
import com.hdu.hdufpga.service.TestRecordService;
import com.hdu.hdufpga.service.UserResourceRecordService;
import com.hdu.hdufpga.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StudentResourceRecordServiceImpl extends MPJBaseServiceImpl<StudentResourceRecordMapper, UserResourceRecordPO> implements UserResourceRecordService {
    @Resource
    private StudentResourceRecordMapper studentResourceRecordMapper;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @DubboReference(check = false)
    private ClassService classService;

    @DubboReference(check = false)
    private TestRecordService testRecordService;

    @Override
    public Boolean updateResourceRecord(UserResourceRecordVO userResourceRecordVO) {
        rocketMQTemplate.asyncSend("resourceRecord", userResourceRecordVO, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("send resourceRecord success : {}", JSONUtil.parse(userResourceRecordVO));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("send resourceRecord error : {} \n exception : {}", JSONUtil.parse(userResourceRecordVO), throwable.getMessage());
            }
        });
        return true;
    }

    private List<UserResourceRecordPO> getRecordByUserId(Integer userId) {
        MPJLambdaWrapper<UserResourceRecordPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .selectAs(UserPO::getId, UserResourceRecordPO::getUserId)
                .selectAs(UserPO::getUsername, UserResourceRecordPO::getUserName)
                .selectAs(UserPO::getRealName, UserResourceRecordPO::getUserRealName)
                .selectAs(ResourcePO::getId, UserResourceRecordPO::getResourceId)
                .selectAs(ResourcePO::getResourceName, UserResourceRecordPO::getResourceName)
                .select(UserResourceRecordPO::getDuration)
                .select(UserResourceRecordPO::getTimes)
                .select(UserResourceRecordPO::getCreateTime)
                .select(UserResourceRecordPO::getUpdateTime)
                .leftJoin(UserPO.class, UserPO::getId, UserResourceRecordPO::getUserId)
                .leftJoin(ResourcePO.class, ResourcePO::getId, UserResourceRecordPO::getResourceId)
                .eq(UserResourceRecordPO::getUserId, userId)
        ;
        return studentResourceRecordMapper.selectJoinList(UserResourceRecordPO.class, wrapper);
    }

    @Override
    public List<StudentStudyRecord> getRecordByClass(Integer classId) {
        String className = classService.getById(classId).getName();
        List<StudentStudyRecord> userRecordList = new ArrayList<>();
        List<UserVO> userList = classService.getStudentListByClassId(classId);
        for (UserVO userVO : userList) {
            StudentStudyRecord userRecord = new StudentStudyRecord();
            userRecord.setClassName(className);
            userRecord.setUsername(userVO.getUsername());
            userRecord.setUserRealName(userVO.getRealName());
            userRecord.setTestCount(testRecordService.getTestCount(userVO.getId(), classId));
            userRecord.setMaxTestScore(testRecordService.getMaxScore(userVO.getId(), classId));
            userRecord.setUserResourceRecord(ConvertUtil.copyList(getRecordByUserId(userVO.getId()), UserResourceRecordVO.class));
            userRecordList.add(userRecord);
        }
        return userRecordList;
    }
}
