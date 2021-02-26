package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysRolesEntity;
import com.dyg.bidcenter.entity.SysUsersRolesEntity;

/**
 * @author merz
 * @Description:
 */
public interface RoleService {

    SysUsersRolesEntity saveUserRole(SysUsersRolesEntity sysUsersRolesEntity);

    SysRolesEntity getRole(SysRolesEntity sysRolesEntity);
}
