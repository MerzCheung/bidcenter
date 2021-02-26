package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysSupplierLabelEntity;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
public interface SupplierLabelService {
    List<SysSupplierLabelEntity> findAll(Integer labelId);
}
