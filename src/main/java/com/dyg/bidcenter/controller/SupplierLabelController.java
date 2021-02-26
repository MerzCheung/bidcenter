package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.entity.SysSupplierLabelEntity;
import com.dyg.bidcenter.service.SupplierLabelService;
import com.dyg.bidcenter.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/supplierLabel")
public class SupplierLabelController {

    @Autowired
    private SupplierLabelService supplierLabelService;

    @GetMapping("/findAll/{labelId}")
    public Object findAll(@PathVariable("labelId") Integer labelId) {
        List<SysSupplierLabelEntity> list = supplierLabelService.findAll(labelId);
        return ResponseUtil.result(ResultCode.SUCCESS, list);
    }
}
