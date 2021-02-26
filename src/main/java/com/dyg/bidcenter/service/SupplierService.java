package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysSupplierEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author merz
 * @Description:
 */
public interface SupplierService {
    SysSupplierEntity add(SysSupplierEntity sysSupplierEntity);
    SysSupplierEntity update(SysSupplierEntity sysSupplierEntity);

    Page<SysSupplierEntity> pageQuery(String kayword, Integer page, Integer size);

    void delete(List<Integer> ids);

    List<SysSupplierEntity> queryAll();

    SysSupplierEntity findByAccount(String account);

    SysSupplierEntity find(SysSupplierEntity sysSupplierEntity);

    Map<Integer, String> getNameMap();

    SysSupplierEntity getPhoneNumber(String id);

    SysSupplierEntity getPhoneNumberById(Integer id);
}
