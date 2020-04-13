package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description:阿里云配置文件实体
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "aliyun-oss")
@Data
public class AliyunOSSProperties {
    /**
     * 服务器地址
     */
    private String endpoint;
    /**
     * OSS身份id
     */
    private String accessKeyId;
    /**
     * 身份密钥
     */
    private String accessKeySecret;

    /**
     * App文件bucketName
     */
    private String bucketApp;

    /**
     * Bucket域名
     */
    private String domain;

}

