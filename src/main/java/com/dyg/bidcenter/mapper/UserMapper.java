package com.dyg.bidcenter.mapper;

import com.dyg.bidcenter.entity.SysBuyerEntity;
import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.model.SysUserModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据account获取账户信息
     *
     * @param account
     * @return
     */
    SysUserEntity getUser(String account);

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId
     * @return
     */
    SysUserEntity getUserById(Integer userId);


    /**
     * 添加用户
     *
     * @param sysUserEntity
     */
    Integer insertUser(SysUserEntity sysUserEntity);

    /**
     * 修改用户
     *
     * @param sysUserModel
     */
    Integer updateUser(SysUserModel sysUserModel);

    List<SysBuyerEntity> getBuyer();
}
