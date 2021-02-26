package com.dyg.bidcenter.controller;

import com.dyg.bidcenter.annotation.CurrentUser;
import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.entity.SysUserEntity;
import com.dyg.bidcenter.model.SysUserModel;
import com.dyg.bidcenter.service.UserService;
import com.dyg.bidcenter.utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Enumeration;

import static com.dyg.bidcenter.cons.CommonCons.USER_INFO;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 修改用户
     *
     * @param sysUserEntity
     * @param sysUserModel
     * @param bindingResult
     * @return
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public Object updateUser(@CurrentUser SysUserEntity sysUserEntity, @Valid @RequestBody SysUserModel sysUserModel, BindingResult bindingResult) {
        sysUserModel.setId(sysUserEntity.getId());
        return userService.updateUser(sysUserModel);
    }

    /**
     * 退出
     *
     * @return
     */
    @GetMapping("/logout")
    @ResponseBody
    public Object logout(HttpSession session) {
        Enumeration<String> em = session.getAttributeNames();
        while (em.hasMoreElements()) {
            session.removeAttribute(em.nextElement());
        }
        session.removeAttribute(USER_INFO);
        SecurityUtils.getSubject().logout();
        return ResponseUtil.result(ResultCode.SUCCESS);
    }


    @GetMapping("/login")
    @ResponseBody
    public ResponseUtil login(@RequestParam("account") String account,
                              @RequestParam("password") String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(account, password, false);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            //此步将 调用realm的认证方法
        } catch (IncorrectCredentialsException e) {
            return ResponseUtil.result(ResultCode.ERROR.getCode(), "密码错误！");
        } catch (AuthenticationException e) {
            return ResponseUtil.result(ResultCode.ERROR.getCode(), e.getCause().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.result(ResultCode.ERROR.getCode(), "登录失败！");
        }
        SysUserEntity user = userService.getUser(account);
        return ResponseUtil.result(ResultCode.SUCCESS, user);
    }
}
