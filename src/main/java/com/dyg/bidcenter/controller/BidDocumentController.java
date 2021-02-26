package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.annotation.CurrentUser;
import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.cons.RoleCons;
import com.dyg.bidcenter.entity.*;
import com.dyg.bidcenter.service.BidDocumentService;
import com.dyg.bidcenter.service.BuyerService;
import com.dyg.bidcenter.service.SupplierService;
import com.dyg.bidcenter.utils.PermissionsUtil;
import com.dyg.bidcenter.utils.ResponseUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dyg.bidcenter.cons.RoleCons.BUYER;
import static com.dyg.bidcenter.cons.RoleCons.BUYER_CHIEF;


/**
 * @author merz
 * @Description:
 */
@RestController
@RequestMapping("/bidDocument")
public class BidDocumentController {

    @Autowired
    private BidDocumentService bidDocumentService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private BuyerService buyerService;

    @GetMapping("/getBidCompanys/{id}")
    public Object getBidCompanys(@PathVariable Integer id) {
        List<SysSupplierEntity> list = bidDocumentService.getBidCompanys(id);
        return ResponseUtil.result(ResultCode.SUCCESS, list);
    }

    @GetMapping("/deleteFile/{id}")
    public Object deleteFile(@PathVariable Integer id) {
        SysBidDocumentEntity sysBidDocumentEntity = bidDocumentService.deleteFile(id);
        return ResponseUtil.result(ResultCode.SUCCESS, sysBidDocumentEntity);
    }

    @GetMapping("/pageQuery/{page}/{size}")
    public Object pageQuery(@CurrentUser SysUserEntity sysUserEntity, @RequestParam(required = false) String kayword,
                            @RequestParam(required = false) String status,
                            @PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Integer id = null;
        if (PermissionsUtil.checkRoles(sysUserEntity, RoleCons.SUPPLIER)) {
            SysSupplierEntity sysSupplierEntity = new SysSupplierEntity();
            sysSupplierEntity.setUserId(sysUserEntity.getId());
            sysSupplierEntity = supplierService.find(sysSupplierEntity);
            id = sysSupplierEntity.getId();
//            status = null;
        }
        String department = null;
        String account = sysUserEntity.getAccount();
        if (PermissionsUtil.checkRoles(sysUserEntity, BUYER_CHIEF)
                || PermissionsUtil.checkRoles(sysUserEntity, BUYER)) {
            SysBuyerEntity byAccount = buyerService.findByAccount(sysUserEntity.getAccount());
            department = byAccount.getDepartment();
        }
        List<SysBidDto> sysBidDto = bidDocumentService.pageQuery(kayword, status, id, department, page, size,account);
        PageInfo<SysBidDto> pageInfo = new PageInfo<>(sysBidDto);
        pageInfo.setPageNum(page);
        pageInfo.setPageSize(size);
        pageInfo.setTotal(bidDocumentService.getTotal(kayword, status, id, department,account));
        return ResponseUtil.result(ResultCode.SUCCESS, pageInfo);
    }
}
