package com.dyg.bidcenter.converter;

import com.dyg.bidcenter.converter.util.SupplierUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author merz
 * @Description:
 */
public class SupplierNameConverter implements ConverterBase {

    private static Map<Integer, String> map = null;

    private synchronized Map<Integer, String> getMap() {
        if (map == null) {
            map = SupplierUtil.getMap();
        }
        return map;
    }

    @Override
    public Object converter(String key) {
        String c = null;
        StringBuffer sb = new StringBuffer();
        if (!StringUtils.isEmpty(key)) {
            String[] keyArr = key.split(",");
            for (String k : keyArr) {
                String a = getMap().get(Integer.valueOf(k));
                sb.append(a).append(",");
            }
            c = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return c;
    }

    @Override
    public Object converter(String key, String type) {
        return null;
    }
}
