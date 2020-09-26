package com.dyg.bidcenter.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.common.ServiceException;
import com.dyg.bidcenter.config.shiro.CurrentUserMethodArgumentResolver;
import com.dyg.bidcenter.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {


    /**
     * 使用阿里 FastJson 作为JSON MessageConverter
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,// 保留空的字段
//                SerializerFeature.WriteNullStringAsEmpty,// String null -> ""
//                SerializerFeature.WriteNullNumberAsZero);// Number null -> 0
//        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);
    }

    /**
     * 统一异常处理
     *
     * @param exceptionResolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, handler, e) -> {
            ResponseUtil result = null;
            // 业务失败的异常，如“账号或密码错误”
            if (e instanceof ServiceException) {
                result = ResponseUtil.result(ResultCode.ERROR.getCode(), e.getCause().getMessage());
            } else if (e instanceof AuthenticationException) {
                result = ResponseUtil.result(ResultCode.AUTHENTICATIONEXCEPTION);
            } else if (e instanceof UnauthorizedException) {
                result = ResponseUtil.result(ResultCode.UNAUTHORIZED);
            } else if (e instanceof com.dyg.bidcenter.common.UnauthorizedException ) {
                result = ResponseUtil.result(ResultCode.NOEXISTUSER);
            } else if (e instanceof NoHandlerFoundException) {
                result = ResponseUtil.result(ResultCode.ERROR.getCode(), "接口 [" + request.getRequestURI() + "] 不存在");
            } else if (e instanceof ServletException) {
                result = ResponseUtil.result(ResultCode.ERROR.getCode(), e.getMessage());
            } else {
                result = ResponseUtil.result(ResultCode.ERROR.getCode(), "接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                String message;
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s", request.getRequestURI(), handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod()
                            .getName(), e.getMessage());
                } else {
                    message = e.getMessage();
                }
                log.error(message, e);
            }
            responseResult(response, result);
            return new ModelAndView();
        });
    }

    /**
     * 解决跨域问题
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }


    /**
     * @param response
     * @param result
     * @Title: responseResult
     * @Description: 响应结果
     * @Reutrn void
     */
    private void responseResult(HttpServletResponse response, ResponseUtil result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
