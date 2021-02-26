package com.dyg.bidcenter.converter;

import com.dyg.bidcenter.converter.util.BuyerUtils;

import java.util.Map;

/**
 * @author merz
 * @Description:
 */
public class BuyerUserNameConverter implements ConverterBase {

    private static Map<String, String> map = null;

    private synchronized Map<String, String> getMap() {
        if (map == null) {
            map = BuyerUtils.getBuyerNameMap();
        }
        return map;
    }

    @Override
    public Object converter(String s) {
        return getMap().get(s);
    }

    @Override
    public Object converter(String key, String type) {
        return null;
    }
}
