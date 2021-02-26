package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.annotation.CurrentUser;
import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.cons.RoleCons;
import com.dyg.bidcenter.entity.SysBuyerEntity;
import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.service.BuyerService;
import com.dyg.bidcenter.utils.PermissionsUtil;
import com.dyg.bidcenter.utils.ResponseUtil;
import com.dyg.bidcenter.valid.BuyerControllerAddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @GetMapping("/delete")
    public Object delete(@RequestParam String ids) {
        List<Integer> listIds = Arrays.stream(ids.split(",")).map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
        buyerService.delete(listIds);
        return ResponseUtil.result(ResultCode.SUCCESS);
    }

    @PostMapping("/add")
    public Object add(@Validated(BuyerControllerAddGroup.class) @RequestBody SysBuyerEntity sysBuyerEntity, BindingResult bindingResult) {
        SysBuyerEntity add = buyerService.add(sysBuyerEntity);
        return ResponseUtil.result(ResultCode.SUCCESS, add);
    }

    @PostMapping("/update")
    public Object update(@RequestBody SysBuyerEntity sysBuyerEntity) {
        SysBuyerEntity update = buyerService.update(sysBuyerEntity);
        return ResponseUtil.result(ResultCode.SUCCESS, update);
    }

    @GetMapping("/pageQuery/{page}/{size}")
    public Object pageQuery(@CurrentUser SysUserEntity sysUserEntity, @PathVariable("page") Integer page, @PathVariable("size") Integer size,
                            @RequestParam(required = false) String kayword) {
        if (PermissionsUtil.checkRoles(sysUserEntity, RoleCons.BUYER) || PermissionsUtil.checkRoles(sysUserEntity, RoleCons.AUDITOR)
                || PermissionsUtil.checkRoles(sysUserEntity, RoleCons.BUYER_ADMIN) || PermissionsUtil.checkRoles(sysUserEntity, RoleCons.BUYER_CHIEF)) {
            SysBuyerEntity sysBuyerEntity = buyerService.findByAccount(sysUserEntity.getAccount());
            return ResponseUtil.result(ResultCode.SUCCESS, sysBuyerEntity);
        }
        Page<SysBuyerEntity> sysSupplierEntities = buyerService.pageQuery(kayword, page, size);
        return ResponseUtil.result(ResultCode.SUCCESS, sysSupplierEntities);
    }
}
