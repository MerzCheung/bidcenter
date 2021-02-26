package com.dyg.bidcenter.repository;

import com.dyg.bidcenter.entity.SysRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author merz
 * @Description:
 */
public interface RoleRepository extends JpaRepository<SysRolesEntity, Integer> {
}
