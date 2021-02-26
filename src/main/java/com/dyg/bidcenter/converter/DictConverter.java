package com.dyg.bidcenter.converter;

import com.dyg.bidcenter.converter.util.DictUtils;

import java.util.Map;

import static com.dyg.bidcenter.cons.DictCons.COMPANY;
import static com.dyg.bidcenter.cons.DictCons.ROLE;

/**
 * @author merz
 * @Description:
 */
public class DictConverter implements ConverterBase {

    private static Map<String, String> companyMap = null;
    private static Map<String, String> roleMap = null;

    private synchronized Map<String, String> getMap(String type) {
        if (COMPANY.equals(type)) {
            if (companyMap == null) {
                companyMap = DictUtils.getMap(type);
            }
            return companyMap;
        }
        if (ROLE.equals(type)) {
            if (roleMap == null) {
                roleMap = DictUtils.getMap(type);
            }
            return roleMap;
        }
        return null;
    }

    @Override
    public Object converter(String key) {
        return null;
    }

    @Override
    public Object converter(String s, String type) {
        if (getMap(type) != null) {
            return getMap(type).get(s);
        }
        return s;
    }
}
