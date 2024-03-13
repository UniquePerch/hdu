package com.hdu.hdufpga.entity.vo;

import com.hdu.hdufpga.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RoleVO extends BaseEntity {
    private String name;
    private String privilegeCharacter;
    private Boolean enable;
}
