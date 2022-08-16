package com.example.service.qa.common.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DoBaseWork {

    /**
     * 描述信息
     * */
    String desc() default "";
}
