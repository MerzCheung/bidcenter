package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysDictEntity;

import java.util.List;
import java.util.Map;

/**
 * @author merz
 * @Description:
 */
public interface DictService {
    List<SysDictEntity> getList(SysDictEntity sysDictEntity);

    Map<String, String> getMap(String type);
}
