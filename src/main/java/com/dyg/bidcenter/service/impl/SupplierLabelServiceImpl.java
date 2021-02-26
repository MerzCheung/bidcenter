package com.dyg.bidcenter.service.impl;

import com.dyg.bidcenter.annotation.ConverterMethod;
import com.dyg.bidcenter.entity.SysSupplierLabelEntity;
import com.dyg.bidcenter.mapper.SupplierLabelMapper;
import com.dyg.bidcenter.service.SupplierLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
@Service
public class SupplierLabelServiceImpl implements SupplierLabelService {

    @Autowired
    private SupplierLabelMapper supplierLabelMapper;

    @Override
    @ConverterMethod
    public List<SysSupplierLabelEntity> findAll(Integer labelId) {
        return supplierLabelMapper.findAll(labelId);
    }
}
