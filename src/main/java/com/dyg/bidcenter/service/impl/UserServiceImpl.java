package com.dyg.bidcenter.service.impl;

import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.common.ServiceException;
import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.mapper.UserMapper;
import com.dyg.bidcenter.model.SysUserModel;
import com.dyg.bidcenter.service.UserService;
import com.dyg.bidcenter.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public SysUserEntity getUser(String account) {
        return userMapper.getUser(account);
    }

    @Override
    public SysUserEntity createUser(SysUserEntity sysUserEntity) {
        SysUserEntity user = userMapper.getUser(sysUserEntity.getAccount());
        if (user == null) {
            sysUserEntity.setPassword(DigestUtils.md5DigestAsHex(sysUserEntity.getPassword().getBytes()));
            Integer i = userMapper.insertUser(sysUserEntity);
            if (i.equals(1)) {
                return sysUserEntity;
            } else {
                throw new ServiceException("用户创建失败");
            }
        } else {
            throw new ServiceException("帐号已存在");
        }
    }

    @Override
    public Object updateUser(SysUserModel sysUserModel) {
        sysUserModel.setPassword(DigestUtils.md5DigestAsHex(sysUserModel.getPassword().getBytes()));
        Integer res = userMapper.updateUser(sysUserModel);
        if (1 == res) {
            return ResponseUtil.result(ResultCode.SUCCESS);
        }
        return ResponseUtil.result(ResultCode.ERROR);
    }
}
