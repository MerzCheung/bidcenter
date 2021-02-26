package com.dyg.bidcenter.service.impl;

import com.dyg.bidcenter.entity.SysRolesEntity;
import com.dyg.bidcenter.entity.SysUsersRolesEntity;
import com.dyg.bidcenter.repository.RoleRepository;
import com.dyg.bidcenter.repository.UserRoleRepository;
import com.dyg.bidcenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author merz
 * @Description:
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public SysUsersRolesEntity saveUserRole(SysUsersRolesEntity sysUsersRolesEntity) {
        if (sysUsersRolesEntity != null && sysUsersRolesEntity.getUserId() != null
        && sysUsersRolesEntity.getRoleId() != null) {
            return userRoleRepository.save(sysUsersRolesEntity);
        }
        return null;
    }

    @Override
    public SysRolesEntity getRole(SysRolesEntity sysRolesEntity) {
        Example<SysRolesEntity> example = Example.of(sysRolesEntity);
        return roleRepository.findOne(example).orElse(null);
    }
}
