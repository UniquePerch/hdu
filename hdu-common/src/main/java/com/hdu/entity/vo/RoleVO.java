package com.hdu.entity.vo;

import com.hdu.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class RoleVO extends BaseEntity {
    private String name;
    private String privilegeCharacter;
    private Boolean enable;
}
