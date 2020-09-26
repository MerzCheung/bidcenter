package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.entity.SysSupplierEntity;
import com.dyg.bidcenter.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/add")
    public SysSupplierEntity add(@Valid @RequestBody SysSupplierEntity sysSupplierEntity, BindingResult bindingResult) {
        return supplierService.add(sysSupplierEntity);
    }

    @PostMapping("/update")
    public SysSupplierEntity update(@Valid @RequestBody SysSupplierEntity sysSupplierEntity, BindingResult bindingResult) {
        return supplierService.update(sysSupplierEntity);
    }
}
