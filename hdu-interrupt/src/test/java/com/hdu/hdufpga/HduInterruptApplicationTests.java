package com.hdu.hdufpga;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.hdu.entity.BaseEntity;
import com.hdu.entity.po.UserPO;
import com.hdu.hdufpga.entity.po.ClassPO;
import com.hdu.hdufpga.mapper.ClassMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class HduInterruptApplicationTests {
    @Resource
    ClassMapper classMapper;
    @Test
    void contextLoads() {
        MPJLambdaWrapper<ClassPO> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .select(ClassPO::getCreateByUserId,ClassPO::getName, BaseEntity::getId,ClassPO::getIsOver,BaseEntity::getIsDeleted,BaseEntity::getCreateTime,BaseEntity::getUpdateTime)
                .selectAs(UserPO::getUsername,ClassPO::getCreateByUsername)
                .leftJoin(UserPO.class,UserPO::getId,ClassPO::getCreateByUserId)
                ;
        List<ClassPO> list = classMapper.selectJoinList(ClassPO.class,wrapper);
        System.out.println(list);
    }
}
