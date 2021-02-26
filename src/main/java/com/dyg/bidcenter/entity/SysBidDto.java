package com.dyg.bidcenter.entity;

import com.dyg.bidcenter.annotation.Converter;
import com.dyg.bidcenter.converter.BuyerUserNameConverter;
import com.dyg.bidcenter.converter.DictConverter;
import com.dyg.bidcenter.converter.SupplierNameConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
@Data
public class SysBidDto {
    private Integer id;
    private String number;
    private String name;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private String time;
    private Integer status;
    private List<SysBidDocumentDto> bidDocumentDtos;

    private String createrAccount;
    @Converter(converter = BuyerUserNameConverter.class)
    private String createrUserName;
    private String createrCompany;
    @Converter(converter = DictConverter.class, type = "company")
    private String createrCompanyName;
    @Converter(converter = SupplierNameConverter.class)
    private String successfulSupplier;
    private String fileName;
    private String fileUrl;
    private Boolean isSuccessful;
    @Converter(converter = SupplierNameConverter.class)
    private String nextBatchSupplier;
    private Boolean isNextBatch;
    private Integer batchNumber;

    private List<SysBidDto> bidDtos;

}
