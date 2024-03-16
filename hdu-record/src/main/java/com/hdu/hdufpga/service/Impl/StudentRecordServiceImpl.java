package com.hdu.hdufpga.service.Impl;

import com.hdu.hdufpga.entity.vo.UserRecord;
import com.hdu.hdufpga.entity.vo.UserResourceRecordVO;
import com.hdu.hdufpga.entity.vo.UserVO;
import com.hdu.hdufpga.service.ClassService;
import com.hdu.hdufpga.service.TestRecordService;
import com.hdu.hdufpga.service.UserRecordService;
import com.hdu.hdufpga.service.UserResourceRecordService;
import com.hdu.hdufpga.util.ConvertUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@DubboService
public class StudentRecordServiceImpl implements UserRecordService {
    @DubboReference
    private ClassService classService;

    @DubboReference
    private TestRecordService testRecordService;

    @Resource
    UserResourceRecordService userResourceRecordService;

    @Override
    public List<UserRecord> getRecordByClass(Integer classId) {
        String className = classService.getById(classId).getName();
        List<UserRecord> userRecordList = new ArrayList<>();
        List<UserVO> userList = classService.getStudentListByClassId(classId);
        for(UserVO userVO : userList) {
            UserRecord userRecord = new UserRecord();
            userRecord.setClassName(className);
            userRecord.setUserRealName(userVO.getRealName());
            userRecord.setTestCount(testRecordService.getTestCount(userVO.getId(),classId));
            userRecord.setMaxTestScore(testRecordService.getMaxScore(userVO.getId(),classId));
            userRecord.setUserResourceRecordVOList(ConvertUtil.copyList(userResourceRecordService.getRecordByUserId(userVO.getId()), UserResourceRecordVO.class));
            userRecordList.add(userRecord);
        }
        return userRecordList;
    }
}
