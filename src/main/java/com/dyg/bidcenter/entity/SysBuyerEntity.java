package com.dyg.bidcenter.entity;

import com.dyg.bidcenter.annotation.Converter;
import com.dyg.bidcenter.converter.DictConverter;
import com.dyg.bidcenter.valid.BuyerControllerAddGroup;
import com.dyg.bidcenter.valid.SupplierControllerAddGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Objects;

import static com.dyg.bidcenter.cons.DictCons.COMPANY;
import static com.dyg.bidcenter.cons.DictCons.ROLE;

/**
 * @author merz
 * @Description:
 */
@Entity
@Table(name = "sys_buyer", schema = "bidcenter", catalog = "")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class SysBuyerEntity {
    private int id;
    private int userId;
    private String username;
    private String phoneNumber;
    @Converter(converter = DictConverter.class, type = COMPANY)
    private String departmentStr;
    private String department;
    @Converter(converter = DictConverter.class, type = ROLE)
    private String positionStr;
    private String position;
    private Integer isValid;
    private Timestamp createdTime;
    @NotBlank(message = "帐号不能为空", groups = BuyerControllerAddGroup.class)
    private String account;
    @NotBlank(message = "密码不能为空", groups = BuyerControllerAddGroup.class)
    private String password;

    @Transient
    public String getDepartmentStr() {
        return departmentStr;
    }

    public void setDepartmentStr(String departmentStr) {
        this.departmentStr = departmentStr;
    }

    @Transient
    public String getPositionStr() {
        return positionStr;
    }

    public void setPositionStr(String positionStr) {
        this.positionStr = positionStr;
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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
    @Column(name = "department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        SysBuyerEntity that = (SysBuyerEntity) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(username, that.username) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(department, that.department) &&
                Objects.equals(position, that.position) &&
                Objects.equals(isValid, that.isValid) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, username, phoneNumber, department, position, isValid, createdTime);
    }
}
