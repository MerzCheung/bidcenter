package com.dyg.bidcenter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserEntity {
    private Integer id;
    private String account;
    private String password;
    private Integer isValid;
    private List<SysRolesEntity> rolesEntityList;
    private List<SysPermissionsEntity> permissionsEntities;
}
