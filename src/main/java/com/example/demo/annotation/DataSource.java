package com.example.demo.annotation;


import com.example.demo.common.DataSourceType;

import java.lang.annotation.*;

/**
 * description: 数据源注解
 * create: 2019/1/29 15:21
 *
 * @author NieMingXin
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    DataSourceType value() default DataSourceType.SLAVE;

}
