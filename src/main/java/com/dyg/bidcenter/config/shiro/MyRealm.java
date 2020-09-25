package com.dyg.bidcenter.config.shiro;

import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.entity.SysPermissionsEntity;
import com.dyg.bidcenter.entity.SysRolesEntity;
import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dyg.bidcenter.cons.CommonCons.USER_INFO;


@Service
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 1.授权方法，在请求需要操作码的接口时会执行此方法。不需要操作码的接口不会执行
     * 2.实际上是 先执行 AuthorizingRealm，自定义realm的父类中的 getAuthorizationInfo方法，
     * 逻辑是先判断缓存中是否有用户的授权信息（用户拥有的操作码），如果有 就直返回不调用自定义 realm的授权方法了，
     * 如果没缓存，再调用自定义realm，去数据库查询。
     * 用库查询一次过后，如果 在安全管理器中注入了 缓存，授权信息就会自动保存在缓存中，下一次调用需要操作码的接口时，
     * 就肯定不会再调用自定义realm授权方法了。   网上有分析AuthorizingRealm，shiro使用缓存的过程
     * 3.AuthorizingRealm 有多个实现类realm，推测可能是把 自定义realm注入了安全管理器，所以才调用自定义的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<SysRolesEntity> rolesEntityList = user.getRolesEntityList();
        if (rolesEntityList != null && rolesEntityList.size() > 0) {
            rolesEntityList.forEach(item -> simpleAuthorizationInfo.addRole(item.getRole()));
        }
        List<SysPermissionsEntity> permissionsEntities = user.getPermissionsEntities();
        Set<String> permission = new HashSet<>();
        if (permissionsEntities != null && permissionsEntities.size() > 0) {
            permissionsEntities.forEach(item -> permission.add(item.getPermission()));
        }
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }



    /**
     * 1.和授权方法一样，AuthenticatingRealm的getAuthenticationInfo，先判断缓存是否有认证信息，没有就调用
     * 但试验，登录之后，再次登录，发现还是调用了认证方法，说明第一次认证登录时，没有将认证信息存到缓存中。不像授权信息，
     * 将缓存注入安全管理器，就自动保存了授权信息。 难道无法 防止故意多次登录 ，按理说不应该啊？
     * 2  可以在登录controller简单用session是否有key 判断是否登录？
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthorizationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        SysUserEntity user = userService.getUser(username);
        if(user==null){throw new AuthorizationException(ResultCode.NOEXISTUSER.getMsg());}

        //进行认证，将正确数据给shiro处理
        //密码不用自己比对，AuthenticationInfo认证信息对象，一个接口，new他的实现类对象SimpleAuthenticationInfo
        /*	第一个参数随便放，可以放user对象，程序可在任意位置获取 放入的对象
         * 第二个参数必须放密码，
         * 第三个参数放 当前realm的名字，因为可能有多个realm*/
        AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        //AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user,user.getPassword(),new MySimpleByteSource(account), this.getName());

        //清缓存中的授权信息，保证每次登陆 都可以重新授权。因为AuthorizingRealm会先检查缓存有没有 授权信息，再调用授权方法
        super.clearCachedAuthorizationInfo(authcInfo.getPrincipals());

        SecurityUtils.getSubject().getSession().setAttribute(USER_INFO, user);

        return authcInfo;
        //返回给安全管理器，securityManager，由securityManager比对数据库查询出的密码和页面提交的密码
        //如果有问题，向上抛异常，一直抛到控制器
    }
}
