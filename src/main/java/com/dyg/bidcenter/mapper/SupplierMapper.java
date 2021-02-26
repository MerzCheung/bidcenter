package com.dyg.bidcenter.mapper;

import com.dyg.bidcenter.entity.SysSupplierEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
@Mapper
public interface SupplierMapper {
    List<SysSupplierEntity> querySupplierByBidId(Integer id);

    SysSupplierEntity findByAccount(String account);

    SysSupplierEntity getPhoneNumber(String id);

    SysSupplierEntity getPhoneNumberById(Integer id);

    SysSupplierEntity getSupplierByPhone(String phoneNumber);

    SysSupplierEntity getSupplierByCompanyName(String companyName);
}
