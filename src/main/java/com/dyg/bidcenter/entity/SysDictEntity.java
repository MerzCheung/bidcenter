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
@Table(name = "sys_dict", schema = "bidcenter", catalog = "")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class SysDictEntity {
    private Integer id;
    private String type;
    private String code;
    private String name;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        SysDictEntity that = (SysDictEntity) o;
        return id == that.id &&
                Objects.equals(type, that.type) &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(isValid, that.isValid) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, code, name, isValid, createdTime);
    }
}
