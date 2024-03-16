package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@TableName("t_user_resource_record")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserResourceRecordPO extends BaseEntity {
    Integer userId;
    String userName;
    String userRealName;
    Integer resourceId;
    String resourceName;
    Long duration;
    Integer times;
}
