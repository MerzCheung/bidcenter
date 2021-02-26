package com.dyg.bidcenter.entity;

import com.dyg.bidcenter.valid.SupplierControllerAddGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author merz
 * @Description:
 */
@Entity
@Table(name = "sys_supplier", schema = "bidcenter", catalog = "")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class SysSupplierEntity {
    private Integer id;
    private Integer userId;
    @NotBlank(message = "帐号不能为空", groups = SupplierControllerAddGroup.class)
    private String account;
    @NotBlank(message = "密码不能为空", groups = SupplierControllerAddGroup.class)
    private String password;
    @NotBlank(message = "公司名不能为空", groups = SupplierControllerAddGroup.class)
    private String companyName;
    private String principal;
    private String phoneNumber;
    private String address;
    private Integer isValid;
    private Timestamp createdTime;
    private String smsCode;

    private List<SysSupplierLabelEntity> labelList;

    @Transient
    public List<SysSupplierLabelEntity> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<SysSupplierLabelEntity> labelList) {
        this.labelList = labelList;
    }

    @Transient
    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }



    @Transient
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "principal")
    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    @Basic
    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        SysSupplierEntity that = (SysSupplierEntity) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(companyName, that.companyName) &&
                Objects.equals(principal, that.principal) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(address, that.address) &&
                Objects.equals(isValid, that.isValid) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, companyName, principal, phoneNumber, address, isValid, createdTime);
    }
}
