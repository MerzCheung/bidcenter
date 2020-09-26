package com.dyg.bidcenter.service.impl;

import com.dyg.bidcenter.entity.SysSupplierEntity;
import com.dyg.bidcenter.mapper.SupplierMapper;
import com.dyg.bidcenter.repository.SupplierRepository;
import com.dyg.bidcenter.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author merz
 * @Description:
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public SysSupplierEntity add(SysSupplierEntity sysSupplierEntity) {
        return supplierRepository.save(sysSupplierEntity);
    }

    @Override
    public SysSupplierEntity update(SysSupplierEntity sysSupplierEntity) {
        return supplierRepository.save(sysSupplierEntity);
    }
}
