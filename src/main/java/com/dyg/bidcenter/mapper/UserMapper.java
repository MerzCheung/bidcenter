package com.dyg.bidcenter.mapper;

import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.model.SysUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    SysUserEntity getUserById(String userId);

    /**
     * 减少用户余额
     *
     * @param userId
     * @param num
     * @return
     */
    Integer decBalance(@Param("userId") int userId, @Param("num") Integer num);

    /**
     * 增加用户余额
     *
     * @param userId
     * @param num
     * @return
     */
    Integer addBalance(@Param("userId") int userId, @Param("num") Integer num);

    /**
     * 添加用户
     *
     * @param sysUserEntity
     */
    void insertUser(SysUserEntity sysUserEntity);

    /**
     * 修改用户
     *
     * @param sysUserModel
     */
    Integer updateUser(SysUserModel sysUserModel);
}
