package com.dyg.bidcenter.repository;

import com.dyg.bidcenter.entity.SysBidDocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author merz
 * @Description:
 */
public interface BidDocumentRepository extends JpaRepository<SysBidDocumentEntity, Integer>, JpaSpecificationExecutor<SysBidDocumentEntity> {
}
