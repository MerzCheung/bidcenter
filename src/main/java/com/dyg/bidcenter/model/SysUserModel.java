package com.dyg.bidcenter.model;

import lombok.Data;

@Data
public class SysUserModel {
    private Integer id;
    private String account;
    private String password;
    private Integer isValid;
}
