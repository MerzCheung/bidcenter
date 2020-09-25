package com.dyg.bidcenter.entity;

import lombok.Data;

@Data
public class SysRolesEntity {
    private int id;
    private String role;
    private String description;
    private Integer pid;
}
