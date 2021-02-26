package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.annotation.CurrentUser;
import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.cons.RoleCons;
import com.dyg.bidcenter.entity.SysSupplierEntity;
import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.service.SupplierService;
import com.dyg.bidcenter.utils.PermissionsUtil;
import com.dyg.bidcenter.utils.ResponseUtil;
import com.dyg.bidcenter.valid.SupplierControllerAddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/delete")
    public Object delete(@RequestParam String ids) {
        List<Integer> listIds = Arrays.stream(ids.split(",")).map(s -> Integer.parseInt(s.trim()))
                .collect(Collectors.toList());
        supplierService.delete(listIds);
        return ResponseUtil.result(ResultCode.SUCCESS);
    }

    @PostMapping("/add")
    public Object add(@Validated(SupplierControllerAddGroup.class) @RequestBody SysSupplierEntity sysSupplierEntity, BindingResult bindingResult) {
        String smsCode = sysSupplierEntity.getSmsCode();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String codes = (String) request.getSession().getAttribute("code");
        if (smsCode.equals(codes)) {
            SysSupplierEntity add = supplierService.add(sysSupplierEntity);
            request.getSession().removeAttribute("code");
            return ResponseUtil.result(ResultCode.SUCCESS, add);
        }else{
            return ResponseUtil.result(ResultCode.CODEERROR);
        }


    }

    @PostMapping("/update")
    public Object update(@RequestBody SysSupplierEntity sysSupplierEntity) {
        SysSupplierEntity update = supplierService.update(sysSupplierEntity);
        return ResponseUtil.result(ResultCode.SUCCESS, update);
    }

    @GetMapping("/pageQuery/{page}/{size}")
    public Object pageQuery(@CurrentUser SysUserEntity sysUserEntity, @PathVariable("page") Integer page, @PathVariable("size") Integer size,
                            @RequestParam(required = false) String kayword) {
        if (PermissionsUtil.checkRoles(sysUserEntity, RoleCons.SUPPLIER)) {
            SysSupplierEntity sysSupplierEntity = supplierService.findByAccount(sysUserEntity.getAccount());
            return ResponseUtil.result(ResultCode.SUCCESS, sysSupplierEntity);
        }
        Page<SysSupplierEntity> sysSupplierEntities = supplierService.pageQuery(kayword, page, size);
        return ResponseUtil.result(ResultCode.SUCCESS, sysSupplierEntities);
    }

    @GetMapping("/queryAll")
    public Object queryAll() {
        List<SysSupplierEntity> sysSupplierEntities = supplierService.queryAll();
        return ResponseUtil.result(ResultCode.SUCCESS, sysSupplierEntities);
    }
}
