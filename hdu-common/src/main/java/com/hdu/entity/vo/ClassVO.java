package com.hdu.entity.vo;

import com.hdu.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class ClassVO extends BaseEntity {
    String name;
    Boolean isOver;

    Integer createByUserId;

    String createByUserName;
}
