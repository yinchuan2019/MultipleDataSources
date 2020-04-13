package com.example.demo.common;

/**
 * description: 数据源枚举类
 * create: 2019/1/29 15:24
 *
 * @author NieMingXin
 */
public enum DataSourceType {
    /**
     * 主库
     */
    MASTER,
    /**
     * 从库
     */
    SLAVE,
    /**
     * 生产库
     */
    PROD
}