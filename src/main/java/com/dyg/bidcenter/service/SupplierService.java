package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysSupplierEntity;

/**
 * @author merz
 * @Description:
 */
public interface SupplierService {
    SysSupplierEntity add(SysSupplierEntity sysSupplierEntity);
    SysSupplierEntity update(SysSupplierEntity sysSupplierEntity);
}
