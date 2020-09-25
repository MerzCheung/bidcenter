package com.dyg.bidcenter.service;


import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.model.SysUserModel;

public interface UserService {
    /**
     * 根据account获取账户信息
     *
     * @param account
     * @return
     */
    SysUserEntity getUser(String account);

    /**
     * 创建用户
     *
     * @return
     */
    Object createUser();

    /**
     * 修改用户
     *
     * @param sysUserModel
     * @return
     */
    Object updateUser(SysUserModel sysUserModel);
}
