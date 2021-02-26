package com.dyg.bidcenter.service.impl;

import com.dyg.bidcenter.entity.SysDictEntity;
import com.dyg.bidcenter.repository.DictRepository;
import com.dyg.bidcenter.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author merz
 * @Description:
 */
@Service("dictService")
public class DictServiceImpl implements DictService {

    @Autowired
    private DictRepository dictRepository;

    @Override
    public List<SysDictEntity> getList(SysDictEntity sysDictEntity) {
        return dictRepository.findAll(Example.of(sysDictEntity));
    }

    @Override
    public Map<String, String> getMap(String type) {
        SysDictEntity sysDictEntity = new SysDictEntity();
        sysDictEntity.setType(type);
        List<SysDictEntity> all = dictRepository.findAll(Example.of(sysDictEntity));
        return all.stream().collect(Collectors.toMap(SysDictEntity::getCode, SysDictEntity::getName));
    }
}
