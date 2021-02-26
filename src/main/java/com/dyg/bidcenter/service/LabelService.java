package com.dyg.bidcenter.service;

import com.dyg.bidcenter.entity.SysLabelEntity;

/**
 * @author merz
 * @Description:
 */
public interface LabelService {
    Object add(SysLabelEntity sysLabelEntity);
    Object delete(Integer id);

    Object queryAll(Integer id);
}
