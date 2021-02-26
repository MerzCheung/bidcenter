package com.dyg.bidcenter.repository;

import com.dyg.bidcenter.entity.SysUsersRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author merz
 * @Description:
 */
public interface UserRoleRepository extends JpaRepository<SysUsersRolesEntity, Integer> {
}
