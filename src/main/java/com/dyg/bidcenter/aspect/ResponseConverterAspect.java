package com.dyg.bidcenter.aspect;

import com.dyg.bidcenter.annotation.Converter;
import com.dyg.bidcenter.converter.ConverterBase;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
@Component
public class ResponseConverterAspect {

    @Around("execution(public * * (..)) && @annotation(com.dyg.bidcenter.annotation.ConverterMethod)")
    public Object requiresPermissions(ProceedingJoinPoint point) throws Throwable {
        Object proceed = point.proceed();
        Set set = new HashSet<>();
        if (proceed instanceof Page) {
            Page page = (Page)proceed;
            foreach(page.getContent(), set);
        } else if (proceed instanceof List) {
            List list = (List)proceed;
            foreach(list, set);
        } else {
            converter(proceed, set);
        }


        set.stream().forEach(item -> {
            Field[] declaredFields1 = item.getClass().getDeclaredFields();
            for (int i = 0 , len = declaredFields1.length; i < len; i++) {
                Field declaredField = declaredFields1[i];
                declaredField.setAccessible(true);
                try {
                    declaredField.set(item, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return proceed;
    }

    private void foreach(List list, Set set) throws InstantiationException, IllegalAccessException {
        for (int i = 0 , len =list.size(); i < len; i++) {
            Object o1 = list.get(i);
            converter(o1, set);
        }
    }

    private void converter(Object o1, Set set) throws IllegalAccessException, InstantiationException {
        Field[] declaredFields = o1.getClass().getDeclaredFields();
        for (int j = 0 , len2 = declaredFields.length; j < len2; j++) {
            Field declaredField = declaredFields[j];
            declaredField.setAccessible(true);
            Object o = declaredField.get(o1);
            if (o != null) {
                if ("java.util.List".equals(declaredField.getType().getName())) {
                    foreach((List)o, set);
                    continue;
                }
                Converter annotation = declaredField.getAnnotation(Converter.class);
                if (annotation != null) {
                    Class<? extends ConverterBase> converter = annotation.converter();
                    String type = annotation.type();
                    ConverterBase converterBase = converter.newInstance();
                    Object value = null;
                    if (!StringUtils.isEmpty(type)) {
                        value = converterBase.converter((String) o, type);
                    } else {
                        value = converterBase.converter((String) o);
                    }
                    declaredField.set(o1, value);
                    set.add(converterBase);
                }
            }
        }
    }
}
