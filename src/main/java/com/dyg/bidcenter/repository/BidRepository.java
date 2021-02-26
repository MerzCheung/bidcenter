package com.dyg.bidcenter.repository;

import com.dyg.bidcenter.entity.SysBidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author merz
 * @Description:
 */
public interface BidRepository extends JpaRepository<SysBidEntity, Integer>, JpaSpecificationExecutor<SysBidEntity> {
}
