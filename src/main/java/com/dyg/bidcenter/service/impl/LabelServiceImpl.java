package com.dyg.bidcenter.service.impl;

import com.dyg.bidcenter.entity.SysLabelEntity;
import com.dyg.bidcenter.repository.LabelRepository;
import com.dyg.bidcenter.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * @author merz
 * @Description:
 */
@Service("labelService")
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public Object add(SysLabelEntity sysLabelEntity) {
        return labelRepository.save(sysLabelEntity);
    }

    @Override
    public Object delete(Integer id) {
        SysLabelEntity sysLabelEntity = labelRepository.findById(id).orElse(null);
        if (sysLabelEntity != null) {
            sysLabelEntity.setIsValid(0);
            return labelRepository.save(sysLabelEntity);
        }
        return null;
    }

    @Override
    public Object queryAll(Integer id) {
        SysLabelEntity sysLabelEntity = new SysLabelEntity();
        sysLabelEntity.setIsValid(1);
        if (id != null) {
            sysLabelEntity.setParentId(id);
        } else {
            sysLabelEntity.setParentId(0);
        }
        Example<SysLabelEntity> of = Example.of(sysLabelEntity);
        return labelRepository.findAll(of);
    }
}
