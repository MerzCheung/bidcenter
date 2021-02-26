package com.dyg.bidcenter.annotation;


import com.dyg.bidcenter.converter.AutoConverter;
import com.dyg.bidcenter.converter.ConverterBase;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Converter {

    Class<? extends ConverterBase> converter() default AutoConverter.class;

    String type() default "";
}
