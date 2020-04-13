package com.example.demo.annotation;


import java.lang.annotation.*;

/**
 * description: 返回code注解(全局捕获业务异常时返回该注解的code)
 * create: 2019/1/29 15:21
 *
 * @author NieMingXin
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseCode {

    String value() default "E00000";
}

