package com.example.demo.aspect;


import com.example.demo.annotation.DataSource;
import com.example.demo.common.DataSourceType;
import com.example.demo.datasource.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * description: 数据源切面
 * create: 2019/1/29 15:22
 *
 * @author NieMingXin
 */
@Component
@Aspect
public class DynamicDataSourceAspect {

    @Before("execution(* com.example.demo.service..*.*(..))")
    public void before(JoinPoint point) {
        try {
            DataSource annotationOfClass = point.getTarget().getClass().getAnnotation(DataSource.class);
            String methodName = point.getSignature().getName();
            Class[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
            Method method = point.getTarget().getClass().getMethod(methodName, parameterTypes);
            DataSource methodAnnotation = method.getAnnotation(DataSource.class);
            methodAnnotation = methodAnnotation == null ? annotationOfClass : methodAnnotation;
            DataSourceType dataSourceType = methodAnnotation != null ? methodAnnotation.value() : DataSourceType.SLAVE;
            DataSourceContextHolder.setDataSource(dataSourceType.name());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @After("execution(* com.example.demo.service..*.*(..))")
    public void after(JoinPoint point) {
        DataSourceContextHolder.clearDataSource();
    }
}
