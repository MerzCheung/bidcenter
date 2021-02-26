package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysBidEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
public interface BidService {
    SysBidEntity add(SysBidEntity sysBidEntity);

    SysBidEntity update(SysBidEntity sysBidEntity);

    void delete(List<Integer> ids);

    List<SysBidEntity> pageBidQuery(String kayword, Integer page, Integer size, String department, String account);
}
