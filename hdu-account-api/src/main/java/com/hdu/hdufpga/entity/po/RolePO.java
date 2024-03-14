package com.hdu.hdufpga.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hdu.hdufpga.entity.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@TableName("t_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePO extends BaseEntity {
    private String name;
    private String privilegeCharacter;
    private Boolean enable;
}
