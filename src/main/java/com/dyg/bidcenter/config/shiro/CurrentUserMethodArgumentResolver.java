package com.dyg.bidcenter.config.shiro;

import com.dyg.bidcenter.annotation.CurrentUser;
import com.dyg.bidcenter.common.UnauthorizedException;
import com.dyg.bidcenter.entity.SysUserEntity;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.dyg.bidcenter.cons.CommonCons.USER_INFO;

/**
 *  增加方法注入，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 * @author liugh
 * @since 2018-05-03
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    public CurrentUserMethodArgumentResolver() {
    }
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(SysUserEntity.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SysUserEntity user = (SysUserEntity) webRequest.getAttribute(USER_INFO, RequestAttributes.SCOPE_SESSION);
        if (user == null) {
            throw new UnauthorizedException("获取用户信息失败");
        }
        return user;
    }
}
