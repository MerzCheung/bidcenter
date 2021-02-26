package com.dyg.bidcenter.converter.util;

import com.dyg.bidcenter.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author merz
 * @Description:
 */
@Component
public class SupplierUtil {
    private static SupplierService supplierService;

    @Autowired
    public SupplierUtil(SupplierService supplierService) {
        SupplierUtil.supplierService=supplierService;
    }

    public static Map<Integer, String> getMap() {
        return supplierService.getNameMap();
    }
}
