package com.dyg.bidcenter.service.impl;

import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.mapper.UserMapper;
import com.dyg.bidcenter.model.SysUserModel;
import com.dyg.bidcenter.service.UserService;
import com.dyg.bidcenter.utils.RandomUtil;
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
    public Object createUser() {
        SysUserEntity sysUserEntity = new SysUserEntity();
        String account;
        while (true) {
            account = RandomUtil.getRandomStringOfNumberByLength(8);
            SysUserEntity user = userMapper.getUser(account);
            if (user == null) {
                break;
            }
        }
        sysUserEntity.setAccount(account);
        sysUserEntity.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        sysUserEntity.setNickName(account);
        sysUserEntity.setBalance(1000);
        sysUserEntity.setPortraitUri("");
        sysUserEntity.setSecretKey(RandomUtil.getUUID());
        userMapper.insertUser(sysUserEntity);
        return ResponseUtil.result(ResultCode.SUCCESS, sysUserEntity);
    }

    @Override
    public Object updateUser(SysUserModel sysUserModel) {
        Integer res = userMapper.updateUser(sysUserModel);
        if (1 == res) {
            return ResponseUtil.result(ResultCode.SUCCESS);
        }
        return ResponseUtil.result(ResultCode.ERROR);
    }
}
