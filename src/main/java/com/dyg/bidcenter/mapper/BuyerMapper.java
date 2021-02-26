package com.dyg.bidcenter.mapper;

import com.dyg.bidcenter.entity.SysBuyerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author merz
 * @Description:
 */
@Mapper
public interface BuyerMapper {
    SysBuyerEntity getEntityByAccount(String account);

    SysBuyerEntity findByAccount(String account);
}
