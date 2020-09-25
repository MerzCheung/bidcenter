package com.dyg.bidcenter.aspect;

import com.dyg.bidcenter.common.ResultCode;
import com.dyg.bidcenter.utils.ResponseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Aspect
@Component
public class ParamValidAspect {

    @Around("execution(* com.dyg.bidcenter.controller.*.*(..)) && args(..,bindingResult)")
    public Object validateParam(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {
        Object retVal;
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            retVal = ResponseUtil.result(ResultCode.PARAM_ERROR.getCode(), allErrors.get(0).getDefaultMessage());
        } else {
            retVal = pjp.proceed();
        }
        return retVal;
    }
}
