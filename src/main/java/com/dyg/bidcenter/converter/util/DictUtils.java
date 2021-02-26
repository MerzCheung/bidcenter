package com.dyg.bidcenter.converter.util;

import com.dyg.bidcenter.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author merz
 * @Description:
 */
@Component
public class DictUtils {

    private static DictService dictService;

    @Autowired
    public DictUtils(DictService dictService) {
        DictUtils.dictService=dictService;
    }

    public static Map<String, String> getMap(String type) {
        return dictService.getMap(type);
    }
}
