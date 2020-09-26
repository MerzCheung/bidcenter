package com.dyg.bidcenter.repository;

import com.dyg.bidcenter.entity.SysSupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author merz
 * @Description:
 */
public interface SupplierRepository extends JpaRepository<SysSupplierEntity, Integer> {
}
