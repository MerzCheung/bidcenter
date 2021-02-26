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
@Table(name = "sys_roles", schema = "bidcenter", catalog = "")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class SysRolesEntity {
    private Integer id;
    private String role;
    private String description;
    private Integer pid;
    private Integer isValid;
    private Timestamp createdTime;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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
        SysRolesEntity that = (SysRolesEntity) o;
        return id == that.id &&
                Objects.equals(role, that.role) &&
                Objects.equals(description, that.description) &&
                Objects.equals(pid, that.pid) &&
                Objects.equals(isValid, that.isValid) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, description, pid, isValid, createdTime);
    }
}
