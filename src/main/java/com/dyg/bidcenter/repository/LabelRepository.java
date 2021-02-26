package com.dyg.bidcenter.repository;

import com.dyg.bidcenter.entity.SysLabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author merz
 * @Description:
 */
public interface LabelRepository extends JpaRepository<SysLabelEntity, Integer>, JpaSpecificationExecutor<SysLabelEntity> {
}
