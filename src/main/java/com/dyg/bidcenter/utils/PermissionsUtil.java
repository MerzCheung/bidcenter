package com.dyg.bidcenter.utils;

import com.dyg.bidcenter.entity.SysPermissionsEntity;
import com.dyg.bidcenter.entity.SysRolesEntity;
import com.dyg.bidcenter.entity.SysUserEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author merz
 * @Description:
 */
public class PermissionsUtil {

    public static boolean checkRoles(SysUserEntity sysUserEntity, String role) {
        if (sysUserEntity == null || StringUtils.isEmpty(role)) {
            return false;
        }
        if (CollectionUtils.isEmpty(sysUserEntity.getRolesEntityList())) {
            return false;
        }
        List<String> collect = sysUserEntity.getRolesEntityList().stream().map(SysRolesEntity::getRole).collect(Collectors.toList());
        return collect.contains(role);
    }

    public static boolean checkPermissions(SysUserEntity sysUserEntity, String permission) {
        if (sysUserEntity == null || StringUtils.isEmpty(permission)) {
            return false;
        }
        if (CollectionUtils.isEmpty(sysUserEntity.getPermissionsEntities())) {
            return false;
        }
        List<String> collect = sysUserEntity.getPermissionsEntities().stream().map(SysPermissionsEntity::getPermission).collect(Collectors.toList());
        return collect.contains(permission);
    }
}
