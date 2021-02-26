package com.dyg.bidcenter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.dyg.bidcenter.entity.SysRolesEntity;
import com.dyg.bidcenter.entity.SysUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidcenterApplicationTests {

    @Test
    public void timeCompare() {
        System.out.println(DateUtil.date());
        System.out.println(DateUtil.compare(DateUtil.parse("2017-03-01"), DateUtil.date()));
    }

    @Test
    public void getPwd() {
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }

    @Test
    public void contextLoads() {
        SysUserEntity u1 = new SysUserEntity();
        u1.setAccount("123");
        u1.setPassword("456");
        List<SysRolesEntity> rolesEntityList = new ArrayList<>();
        SysRolesEntity e1 = new SysRolesEntity();
        e1.setId(1);
        rolesEntityList.add(e1);
        u1.setRolesEntityList(rolesEntityList);
        SysUserEntity u2 = new SysUserEntity();
        u2.setAccount("789");
        u2.setId(111);
        List<SysRolesEntity> rolesEntityList2 = new ArrayList<>();
        SysRolesEntity e2 = new SysRolesEntity();
        e2.setId(2);
        rolesEntityList2.add(e2);
        u2.setRolesEntityList(rolesEntityList2);
//        BeanUtil.copyProperties(u2, u1);
//        BeanUtil.copyProperties(u2, u1, false);
//        BeanUtil.copyProperties(u2, u1, true);
        BeanUtil.copyProperties(u2, u1, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        System.out.println(u1);
    }

}
