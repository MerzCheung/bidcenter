package com.dyg.bidcenter.entity;

import com.dyg.bidcenter.annotation.Converter;
import com.dyg.bidcenter.converter.BuyerUserNameConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author merz
 * @Description:
 */
@Entity
@Table(name = "sys_bid", schema = "bidcenter", catalog = "")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class SysBidEntity {
    private Integer id;
    private String number;
    private String name;
    private String createrAccount;
    private String createrCompany;
    @Converter(converter = BuyerUserNameConverter.class)
    private String creater;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
    private Date time;
    private Integer isValid;
    private Timestamp createdTime;
    private Integer batchNumber;
    private String nextBatchSupplier;

    private List<SysSupplierEntity> suppliers;
    private List<String> supplierNames;

    private String successfulSupplier;
    //private List<String> successfulSupplier;

    private String status;
    private String fileName;
    private String fileUrl;

    public String getNextBatchSupplier() {
        return nextBatchSupplier;
    }

    public void setNextBatchSupplier(String nextBatchSupplier) {
        this.nextBatchSupplier = nextBatchSupplier;
    }

    @Transient
    public List<String> getSupplierNames() {
        return supplierNames;
    }

    public void setSupplierNames(List<String> supplierNames) {
        this.supplierNames = supplierNames;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    @Basic
    @Column(name = "batch_number")
    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }


    @Transient
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Transient
    public List<SysSupplierEntity> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SysSupplierEntity> suppliers) {
        this.suppliers = suppliers;
    }

    @Basic
    @Column(name = "successful_supplier")
    public String getSuccessfulSupplier() {
        return successfulSupplier;
    }

    public void setSuccessfulSupplier(String successfulSupplier) {
        this.successfulSupplier = successfulSupplier;
    }

//    @Basic
//    @Column(name = "successful_supplier")
//    public List<String> getSuccessfulSupplier() {
//        return successfulSupplier;
//    }
//
//    public void setSuccessfulSupplier(List<String> successfulSupplier) {
//        this.successfulSupplier = successfulSupplier;
//    }


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Transient
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Basic
    @Column(name = "creater_account")
    public String getCreaterAccount() {
        return createrAccount;
    }

    public void setCreaterAccount(String createrAccount) {
        this.createrAccount = createrAccount;
    }

    @Basic
    @Column(name = "creater_company")
    public String getCreaterCompany() {
        return createrCompany;
    }

    public void setCreaterCompany(String createrCompany) {
        this.createrCompany = createrCompany;
    }

    @Basic
    @Column(name = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Basic
    @Column(name = "is_valid")
    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Basic
    @Column(name = "created_time", updatable = false)
    @CreatedDate
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysBidEntity that = (SysBidEntity) o;
        return id.equals(that.id) &&
                Objects.equals(number, that.number) &&
                Objects.equals(name, that.name) &&
                Objects.equals(creater, that.creater) &&
                Objects.equals(createrAccount, that.createrAccount) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(fileUrl, that.fileUrl) &&
                Objects.equals(time, that.time) &&
                Objects.equals(isValid, that.isValid) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(createrCompany, that.createrCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, name, creater, createrAccount, time, fileName, fileUrl, isValid, createrCompany, createdTime);
    }
}
