package com.dyg.bidcenter.repository;

import com.dyg.bidcenter.entity.SysBuyerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author merz
 * @Description:
 */
public interface BuyerRepository extends JpaRepository<SysBuyerEntity, Integer>, JpaSpecificationExecutor<SysBuyerEntity> {
}
