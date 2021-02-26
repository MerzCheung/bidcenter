package com.dyg.bidcenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import com.dyg.bidcenter.annotation.ConverterMethod;
import com.dyg.bidcenter.entity.SysBidDocumentEntity;
import com.dyg.bidcenter.entity.SysBidDto;
import com.dyg.bidcenter.entity.SysBidEntity;
import com.dyg.bidcenter.entity.SysSupplierEntity;
import com.dyg.bidcenter.mapper.BidDocumentMapper;
import com.dyg.bidcenter.mapper.BidMapper;
import com.dyg.bidcenter.repository.BidDocumentRepository;
import com.dyg.bidcenter.repository.BidRepository;
import com.dyg.bidcenter.service.BidDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author merz
 * @Description:
 */
@Service("bidDocumentService")
public class BidDocumentServiceImpl implements BidDocumentService {

    @Autowired
    private BidMapper bidMapper;
    @Autowired
    private BidDocumentRepository bidDocumentRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private BidDocumentMapper bidDocumentMapper;

    @Override
    @ConverterMethod
    public List<SysBidDto> pageQuery(String kayword, String status, Integer supplierId, String department, Integer page, Integer size, String account) {
        Integer pageNo = (page-1)*size;
        List<SysBidDto> sysBidDtos = bidMapper.pageQuery(kayword, status, supplierId, department, pageNo, size,account);
        for (SysBidDto sysBidDto : sysBidDtos) {
            sysBidDto.setCreaterUserName(sysBidDto.getCreaterAccount());
            sysBidDto.setCreaterCompanyName(sysBidDto.getCreaterCompany());

            for(SysBidDto item2: sysBidDto.getBidDtos()) {
                // 中标标志
                if(!StringUtils.isEmpty(item2.getSuccessfulSupplier())){
                    if (supplierId != null) {
                        if (Arrays.asList(item2.getSuccessfulSupplier().split(",")).contains(supplierId.toString())) {
                            item2.setIsSuccessful(true);
                        } else {
                            item2.setIsSuccessful(false);
                        }
                    } else {
                        item2.setIsSuccessful(true);
                    }
                } else {
                    item2.setIsSuccessful(false);
                }
                // 参与下一轮标志
                if(!StringUtils.isEmpty(item2.getNextBatchSupplier())){
                    if (supplierId != null) {
                        if (Arrays.asList(item2.getNextBatchSupplier().split(",")).contains(supplierId.toString())) {
                            item2.setIsNextBatch(true);
                        } else {
                            item2.setIsNextBatch(false);
                        }
                    } else {
                        item2.setIsNextBatch(true);
                    }
                } else {
                    item2.setIsNextBatch(false);
                }
            }
        }
        return sysBidDtos;
    }

    @Override
    public Integer getTotal(String kayword, String status, Integer supplierId, String department,String account) {
        return bidMapper.getTotal(kayword, status, supplierId, department,account);
    }

    @Override
    public SysBidDocumentEntity update(SysBidDocumentEntity sysBidDocumentEntity) {
        SysBidEntity sysBidEntity = bidRepository.findById(sysBidDocumentEntity.getBidId()).orElse(null);
        if (sysBidEntity != null && DateUtil.compare(sysBidEntity.getTime(), new Date()) > 0) {
            SysBidDocumentEntity sysBidDocumentEntity1 = bidDocumentRepository.findById(sysBidDocumentEntity.getId()).orElse(null);
            if (sysBidDocumentEntity1 != null) {
                BeanUtil.copyProperties(sysBidDocumentEntity, sysBidDocumentEntity1, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
                return bidDocumentRepository.save(sysBidDocumentEntity1);
            }
        }
        return null;
    }

    @Override
    public SysBidDocumentEntity deleteFile(Integer id) {
        SysBidDocumentEntity sysBidDocumentEntity = bidDocumentRepository.findById(id).orElse(null);
        if (sysBidDocumentEntity != null) {
            sysBidDocumentEntity.setFileName(null);
            sysBidDocumentEntity.setFileUrl(null);
            sysBidDocumentEntity.setTime(null);
            return bidDocumentRepository.save(sysBidDocumentEntity);
        }
        return null;
    }

    @Override
    public List<SysSupplierEntity> getBidCompanys(Integer bidId) {
        return bidDocumentMapper.getBidCompanys(bidId);
    }
}
