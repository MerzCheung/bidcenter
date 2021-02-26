package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.annotation.CurrentUser;
import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.entity.*;
import com.dyg.bidcenter.mapper.BidMapper;
import com.dyg.bidcenter.service.BidService;
import com.dyg.bidcenter.service.BuyerService;
import com.dyg.bidcenter.service.SupplierService;
import com.dyg.bidcenter.utils.PermissionsUtil;
import com.dyg.bidcenter.utils.ResponseUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.dyg.bidcenter.cons.RoleCons.BUYER;
import static com.dyg.bidcenter.cons.RoleCons.BUYER_CHIEF;

/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/bid")
public class BidController {

    @Autowired
    private BidService bidService;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private BidMapper bidMapper;

    @GetMapping("/pageQuery/{page}/{size}")
    public Object pageQuery(@CurrentUser SysUserEntity sysUserEntity, @PathVariable("page") Integer page,
                            @PathVariable("size") Integer size, @RequestParam(required = false) String kayword) {
        String department = null;
        String account = sysUserEntity.getAccount();
        if (PermissionsUtil.checkRoles(sysUserEntity, BUYER_CHIEF)
                || PermissionsUtil.checkRoles(sysUserEntity, BUYER)) {
            SysBuyerEntity byAccount = buyerService.findByAccount(sysUserEntity.getAccount());
            department = byAccount.getDepartment();
        }
        List<SysBidEntity> sysBidEntities = bidService.pageBidQuery(kayword, page, size, department, account);
        PageInfo<SysBidEntity> pageInfo = new PageInfo<>(sysBidEntities);
        pageInfo.setPageNum(page);
        pageInfo.setPageSize(size);
        pageInfo.setTotal(bidMapper.getStatusTotal(kayword, department, account));
        return ResponseUtil.result(ResultCode.SUCCESS, pageInfo);
    }

    @GetMapping("/delete")
    public Object delete(@RequestParam String ids) {
        List<Integer> listIds = Arrays.stream(ids.split(",")).map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
        bidService.delete(listIds);
        return ResponseUtil.result(ResultCode.SUCCESS);
    }

    @RequiresRoles(value = {BUYER, BUYER_CHIEF}, logical = Logical.OR)
    @PostMapping("/add")
    public Object add(@CurrentUser SysUserEntity sysUserEntity, @RequestBody SysBidEntity sysBidEntity) {
        sysBidEntity.setCreaterAccount(sysUserEntity.getAccount());
        if (CollectionUtils.isNotEmpty(sysBidEntity.getSuppliers())) {
            sysBidEntity.setSuppliers(sysBidEntity.getSuppliers().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                    () -> new TreeSet<>(Comparator.comparing(SysSupplierEntity::getCompanyName))), ArrayList::new)));
        }
        SysBidEntity add = bidService.add(sysBidEntity);
        return ResponseUtil.result(ResultCode.SUCCESS, add);
    }

    @PostMapping("/update")
    public Object update(@RequestBody SysBidEntity sysBidEntity) {
        StringBuffer sb = new StringBuffer();
        if (CollectionUtils.isNotEmpty(sysBidEntity.getSuppliers())) {
            sysBidEntity.setSuppliers(sysBidEntity.getSuppliers().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                    () -> new TreeSet<>(Comparator.comparing(SysSupplierEntity::getCompanyName))), ArrayList::new)));
        }
        SysBidEntity add = bidService.update(sysBidEntity);
        if (!StringUtils.isEmpty(sysBidEntity.getSuccessfulSupplier())) {
            String[] successfulSupplierArr = sysBidEntity.getSuccessfulSupplier().split(",");
            SysSupplierEntity entity = new SysSupplierEntity();
            for (String successfulSupplier : successfulSupplierArr) {
                entity.setId(Integer.valueOf(successfulSupplier));
                SysSupplierEntity sysSupplierEntity = supplierService.find(entity);
                StringBuffer b = sb.append(sysSupplierEntity.getCompanyName()).append(",");
            }
            String companyNames = sb.deleteCharAt(sb.length() - 1).toString();
            sysBidEntity.setSuccessfulSupplier(companyNames);
//            entity.setId(Integer.valueOf(sysBidEntity.getSuccessfulSupplier()));
//            SysSupplierEntity sysSupplierEntity = supplierService.find(entity);
//            sysBidEntity.setSuccessfulSupplier(sysSupplierEntity.getCompanyName());
        }
        return ResponseUtil.result(ResultCode.SUCCESS, add);
    }

    @GetMapping("/queryDm")
    public Object queryDm(@RequestParam String account) {
        SysBuyerEntity byAccount = buyerService.findByAccount(account);
        String department = byAccount.getDepartment();
        return ResponseUtil.result(ResultCode.SUCCESS, department);
    }
}
