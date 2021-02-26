package com.dyg.bidcenter.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author merz
 * @Description:
 */
@Entity
@Table(name = "sys_bid_document", schema = "bidcenter", catalog = "")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class SysBidDocumentEntity {
    private Integer id;
    private Integer bidId;
    private Integer supplierId;
    private String fileName;
    private String fileUrl;
    private String time;
    private Integer isValid;
    private Timestamp createdTime;

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
    @Column(name = "bid_id")
    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    @Basic
    @Column(name = "supplier_id")
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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
        SysBidDocumentEntity that = (SysBidDocumentEntity) o;
        return id == that.id &&
                bidId == that.bidId &&
                supplierId == that.supplierId &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(fileUrl, that.fileUrl) &&
                Objects.equals(time, that.time) &&
                Objects.equals(isValid, that.isValid) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bidId, supplierId, fileName, fileUrl, time, isValid, createdTime);
    }
}
