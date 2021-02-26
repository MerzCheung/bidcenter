package com.dyg.bidcenter.repository;

import com.dyg.bidcenter.entity.SysDictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author merz
 * @Description:
 */
public interface DictRepository extends JpaRepository<SysDictEntity, Integer>, JpaSpecificationExecutor<SysDictEntity> {
}
