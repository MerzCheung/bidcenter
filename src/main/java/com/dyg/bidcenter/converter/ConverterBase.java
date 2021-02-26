package com.dyg.bidcenter.converter;

public interface ConverterBase {

    public Object converter(String key);

    public Object converter(String key, String type);
}
