package com.hdu.hdufpga.entity.vo;

import com.hdu.hdufpga.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserResourceRecordVO extends BaseEntity {
    Integer userId;
    String userName;
    String userRealName;
    Integer resourceId;
    String resourceName;
    Long duration;
    Integer times;
}
