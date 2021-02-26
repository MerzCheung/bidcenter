package com.dyg.bidcenter.mapper;

import com.dyg.bidcenter.entity.SysSupplierEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
@Mapper
public interface BidDocumentMapper {

    List<SysSupplierEntity> getBidCompanys(Integer bidId);
}
