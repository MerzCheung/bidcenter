package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysBuyerEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @author merz
 * @Description:
 */
public interface BuyerService {
    SysBuyerEntity add(SysBuyerEntity sysBuyerEntity);
    SysBuyerEntity update(SysBuyerEntity sysBuyerEntity);

    Page<SysBuyerEntity> pageQuery(String kayword, Integer page, Integer size);

    void delete(List<Integer> listIds);

    SysBuyerEntity findByAccount(String account);

    Map<String, String> getBuyerNameMap();
}
