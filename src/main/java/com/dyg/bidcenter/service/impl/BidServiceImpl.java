package com.dyg.bidcenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.dyg.bidcenter.annotation.ConverterMethod;
import com.dyg.bidcenter.common.ServiceException;
import com.dyg.bidcenter.entity.*;
import com.dyg.bidcenter.mapper.BidMapper;
import com.dyg.bidcenter.mapper.BuyerMapper;
import com.dyg.bidcenter.mapper.SupplierMapper;
import com.dyg.bidcenter.repository.BidDocumentRepository;
import com.dyg.bidcenter.repository.BidRepository;
import com.dyg.bidcenter.service.BidService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author merz
 * @Description:
 */
@Service("bidService")
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private BuyerMapper buyerMapper;
    @Autowired
    private BidDocumentRepository bidDocumentRepository;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private BidMapper bidMapper;

    @Override
    public SysBidEntity update(SysBidEntity sysBidEntity) {
        SysBidEntity sysBidEntity1 = bidRepository.findById(sysBidEntity.getId()).orElse(null);
        if (sysBidEntity1 != null) {
            BeanUtil.copyProperties(sysBidEntity, sysBidEntity1, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            SysBidEntity save = bidRepository.save(sysBidEntity1);
            List<SysSupplierEntity> suppliers = sysBidEntity.getSuppliers();
            if (!CollectionUtils.isEmpty(suppliers)) {
                // 新入参
                List<String> supplierIds = suppliers.stream().map(item -> item.getId().toString()).collect(Collectors.toList());
                SysBidDocumentEntity sysBidDocumentEntity = new SysBidDocumentEntity();
                sysBidDocumentEntity.setBidId(save.getId());
                sysBidDocumentEntity.setIsValid(1);
                Example<SysBidDocumentEntity> of = Example.of(sysBidDocumentEntity);
                // 历史所有的
                List<SysBidDocumentEntity> all = bidDocumentRepository.findAll(of);
                List<String> collect = all.stream().map(item -> item.getSupplierId().toString()).collect(Collectors.toList());

                // 删除
                List<String> collect1 = collect.stream().filter(id -> !supplierIds.contains(id)).collect(Collectors.toList());
                collect1.forEach(id -> {
                    SysBidDocumentEntity entity = new SysBidDocumentEntity();
                    entity.setBidId(save.getId());
                    entity.setSupplierId(Integer.valueOf(id));
                    entity.setIsValid(1);
                    SysBidDocumentEntity sysBidDocumentEntity1 = bidDocumentRepository.findOne(Example.of(entity)).orElse(null);
                    if (sysBidDocumentEntity1 != null) {
                        sysBidDocumentEntity1.setIsValid(0);
                        bidDocumentRepository.save(sysBidDocumentEntity1);
                    }
                });
                // 新增
                List<String> collect2 = supplierIds.stream().filter(item -> !collect.contains(item)).collect(Collectors.toList());
                collect2.forEach(item -> {
                    SysBidDocumentEntity sysBidDocumentEntity2 = new SysBidDocumentEntity();
                    sysBidDocumentEntity2.setBidId(save.getId());
                    sysBidDocumentEntity2.setSupplierId(Integer.valueOf(item));
                    bidDocumentRepository.save(sysBidDocumentEntity2);
                });
            }
            return sysBidEntity;
        }
        return null;
    }

    @Transactional
    @Override
    public SysBidEntity add(SysBidEntity sysBidEntity) {
        if (sysBidEntity.getId() == null) {
            sysBidEntity.setBatchNumber(1);

            SysBidEntity sysBidEntity2 = new SysBidEntity();
            sysBidEntity2.setNumber(sysBidEntity.getNumber());
            sysBidEntity2.setIsValid(1);
            Example<SysBidEntity> of1 = Example.of(sysBidEntity2);
            List<SysBidEntity> all1 = bidRepository.findAll(of1);
            if (!CollectionUtils.isEmpty(all1)) {
                throw new ServiceException("标书编号已存在");
            }
            SysBuyerEntity sysBuyerEntity = buyerMapper.getEntityByAccount(sysBidEntity.getCreaterAccount());
            sysBidEntity.setCreaterAccount(sysBidEntity.getCreaterAccount());
            if(!StringUtils.isEmpty(sysBidEntity.getCreaterCompany())){
               sysBidEntity.setCreaterCompany(sysBidEntity.getCreaterCompany());
            }
            SysBidEntity save = bidRepository.save(sysBidEntity);
            sysBidEntity.getSuppliers().forEach(item -> {
                SysBidDocumentEntity sysBidDocumentEntity = new SysBidDocumentEntity();
                sysBidDocumentEntity.setBidId(save.getId());
                sysBidDocumentEntity.setSupplierId(item.getId());
                bidDocumentRepository.save(sysBidDocumentEntity);
            });
            return save;
        } else {
            SysBidEntity sysBidEntity1 = bidRepository.findById(sysBidEntity.getId()).orElse(null);
            if (sysBidEntity1 != null) {
                SysBidEntity sysBidEntity2 = new SysBidEntity();
                BeanUtil.copyProperties(sysBidEntity1, sysBidEntity2, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                // 修改上一个版本的‘是否有下一个批次’
                sysBidEntity1.setNextBatchSupplier(sysBidEntity.getSuccessfulSupplier());
                bidRepository.save(sysBidEntity1);
                // 新建一个批次
                sysBidEntity2.setBatchNumber(sysBidEntity1.getBatchNumber() + 1);
                sysBidEntity2.setId(null);
                sysBidEntity2.setTime(sysBidEntity.getTime());
                SysBidEntity save = bidRepository.save(sysBidEntity2);
                Arrays.asList(sysBidEntity.getSuccessfulSupplier().split(",")).forEach(item -> {
                    SysBidDocumentEntity sysBidDocumentEntity = new SysBidDocumentEntity();
                    sysBidDocumentEntity.setBidId(save.getId());
                    sysBidDocumentEntity.setSupplierId(Integer.valueOf(item));
                    bidDocumentRepository.save(sysBidDocumentEntity);
                });
                return save;
            }
            return null;
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(id -> {
            SysBidEntity sysBidEntity = bidRepository.findById(id).orElse(null);
            if (sysBidEntity != null) {
                sysBidEntity.setIsValid(0);
                bidRepository.save(sysBidEntity);
            }
        });
    }

    @Override
    @ConverterMethod
    public List<SysBidEntity> pageBidQuery(String kayword, Integer page, Integer size, String department, String account) {
        Integer pageNo = (page-1)*size;
        List<SysBidEntity> all = bidMapper.pageBidQuery(kayword,pageNo,size,department,account);
        all.forEach(item -> {
            int compare = DateUtil.compare(item.getTime(), DateUtil.date());
            if (compare > 0) {
                item.setStatus("未开标");
            } else {
                item.setStatus("已开标");
            }

            List<SysSupplierEntity> sysSupplierEntity = supplierMapper.querySupplierByBidId(item.getId());
            if (item.getStatus().equals("未开标")) {
                sysSupplierEntity = sysSupplierEntity.stream().filter(i -> i.getIsValid() == 1).collect(Collectors.toList());
            }
            List<String> collect = sysSupplierEntity.stream().map(SysSupplierEntity::getCompanyName).collect(Collectors.toList());
            item.setSupplierNames(collect);

            item.setSuppliers(sysSupplierEntity);

            item.setCreater(item.getCreaterAccount());
        });
        return all;
    }
}
