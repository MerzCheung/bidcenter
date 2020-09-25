package com.dyg.bidcenter.model;

import lombok.Data;

@Data
public class SysUserModel {
    private Integer id;
    private String account;
    private String phoneNum;
    private String password;
    private String secretKey;
    private String nickName;
    private String portraitUri;
    private Integer balance;
}
