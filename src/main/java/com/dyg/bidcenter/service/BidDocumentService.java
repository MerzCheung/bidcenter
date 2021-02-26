package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysBidDocumentEntity;
import com.dyg.bidcenter.entity.SysBidDto;
import com.dyg.bidcenter.entity.SysSupplierEntity;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
public interface BidDocumentService {
    List<SysBidDto> pageQuery(String kayword, String status, Integer supplierId, String department, Integer page, Integer size,String account);
    Integer getTotal(String kayword, String status, Integer supplierId, String department,String account);
    SysBidDocumentEntity update(SysBidDocumentEntity sysBidDocumentEntity);

    SysBidDocumentEntity deleteFile(Integer id);

    List<SysSupplierEntity> getBidCompanys(Integer bidId);
}
