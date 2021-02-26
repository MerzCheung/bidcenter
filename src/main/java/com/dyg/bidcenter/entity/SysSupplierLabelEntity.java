package com.dyg.bidcenter.entity;

import com.dyg.bidcenter.annotation.Converter;
import com.dyg.bidcenter.converter.SupplierNameConverter;
import lombok.Data;

/**
 * @author merz
 * @Description:
 */
@Data
public class SysSupplierLabelEntity {

    private Integer id;
    private Integer labelId;

    private String name;

    private Integer supplierId;
    @Converter(converter = SupplierNameConverter.class)
    private String supplierName;
}
