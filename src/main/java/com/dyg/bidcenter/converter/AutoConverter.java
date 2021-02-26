package com.dyg.bidcenter.converter;

public class AutoConverter implements ConverterBase {

    @Override
    public Object converter(String key) {
        return key;
    }

    @Override
    public Object converter(String key, String type) {
        return key;
    }
}
