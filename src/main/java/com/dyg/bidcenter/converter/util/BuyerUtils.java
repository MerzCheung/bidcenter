package com.dyg.bidcenter.converter.util;

import com.dyg.bidcenter.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author merz
 * @Description:
 */
@Component
public class BuyerUtils {

    private static BuyerService buyerService;

    @Autowired
    public BuyerUtils(BuyerService buyerService) {
        BuyerUtils.buyerService=buyerService;
    }

    public static Map<String, String> getBuyerNameMap() {
        return buyerService.getBuyerNameMap();
    }
}
