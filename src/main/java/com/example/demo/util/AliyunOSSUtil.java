package com.example.demo.util;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.example.demo.common.ApiException;
import com.example.demo.config.AliyunOSSProperties;
import com.example.demo.enums.FileTypeEnum;
import com.example.demo.exception.OSSNoSuchBucketException;
import com.example.demo.exception.OSSNoSuchKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * description: 阿里云util
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
@Component
public class AliyunOSSUtil {

    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    private static Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);

    /**
     * 文件不存在
     */
    private final String NO_SUCH_KEY = "NoSuchKey";
    /**
     * 存储空间不存在
     */
    private final String NO_SUCH_BUCKET = "NoSuchBucket";

    /**
     * create: 2020/2/18 12:08
     * description: 上传文件到阿里云 OSS 服务器
     *
     * @param files:        文件
     * @param fileTypeEnum: 文件类型枚举
     * @param bucketName:   oss桶名
     * @param storagePath:  储存文件夹路径,传null则为上传到根目录
     * @param prefix:       #Bucket公网域名
     * @return java.util.List<java.lang.String>
     * @author niemingxin
     */
    public List<String> uploadFile(MultipartFile[] files, FileTypeEnum fileTypeEnum, String bucketName, String storagePath, String prefix) {
        //创建OSSClient实例
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        List<String> fileIds = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                //创建一个唯一的文件名，类似于id，就是保存在OSS服务器上文件的文件名(自定义文件名)
                String fileName = this.createFileEnum(fileTypeEnum.getCode(), file.getOriginalFilename());
                InputStream inputStream = file.getInputStream();
                ObjectMetadata objectMetadata = new ObjectMetadata();
                //设置数据流里有多少个字节可以读取
                objectMetadata.setContentLength(inputStream.available());
                objectMetadata.setCacheControl("no-cache");
                objectMetadata.setHeader("Pragma", "no-cache");
                String substring = fileName.substring(fileName.lastIndexOf("."));
                if ((".jpeg".equalsIgnoreCase(substring) ||
                        ".jpg".equalsIgnoreCase(substring) ||
                        ".png".equalsIgnoreCase(substring)) ||
                        ".webp".equalsIgnoreCase(substring)) {
                    objectMetadata.setContentType("image/jpg");
                } else {
                    objectMetadata.setContentType(file.getContentType());
                }
                objectMetadata.setContentDisposition("inline;filename=" + fileName);
                fileName = StrUtil.isNotBlank(storagePath) ? storagePath + "/" + fileName : fileName;
                //上传文件
                PutObjectResult result = ossClient.putObject(bucketName, fileName, inputStream, objectMetadata);
                logger.info("Aliyun OSS AliyunOSSUtil.uploadFileToAliyunOSS,result:{}", result);
                fileIds.add(prefix + fileName);
            }
        } catch (IOException e) {
            logger.error("Aliyun OSS uploadFile fail,fileTypeEnum={},bucketName={},storagePath={},prefix={},cause:{}", fileTypeEnum, bucketName, storagePath, prefix, e.getCause());
        } finally {
            ossClient.shutdown();
        }
        return fileIds;
    }

    /**
     * create: 2020/2/18 12:17
     * description: 删除文件
     *
     * @param fileName:   文件名
     * @param bucketName: oss 桶名
     * @author niemingxin
     */
    public void deleteFile(String fileName, String bucketName) {
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        ossClient.deleteObject(bucketName, fileName);
        shutdown(ossClient);
    }

    /**
     * create: 2020/2/18 12:20
     * description: 根据图片全路径删除就图片
     *
     * @param imgUrl:     图片全路径
     * @param bucketName: 存储路径
     * @author niemingxin
     */
    public void delImg(String imgUrl, String bucketName) {
        if (StrUtil.isBlank(imgUrl)) {
            return;
        }
        //问号
        int index = imgUrl.indexOf('?');
        if (index != -1) {
            imgUrl = imgUrl.substring(0, index);
        }
        int slashIndex = imgUrl.lastIndexOf('/');
        String fileId = imgUrl.substring(slashIndex + 1);
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        ossClient.deleteObject(bucketName, fileId);
        shutdown(ossClient);
    }


    /**
     * create: 2020/2/18 12:20
     * description: 判断文件是否存在
     *
     * @param fileName:   文件名称
     * @param bucketName: 文件储存空间名称
     * @return boolean  true:存在,false:不存在
     * @author niemingxin
     */
    public boolean doesObjectExist(String fileName, String bucketName) {
        Validate.notEmpty(fileName, "fileName can be not empty");
        Validate.notEmpty(bucketName, "bucketName can be not empty");
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        try {
            return ossClient.doesObjectExist(bucketName, fileName);
        } finally {
            shutdown(ossClient);
        }

    }

    /**
     * 复制文件
     *
     * @param fileName              源文件名称
     * @param bucketName            源储存空间名称
     * @param destinationBucketName 目标储存空间名称
     * @param destinationObjectName 目标文件名称
     */
    public void ossCopyObject(String fileName, String bucketName, String destinationBucketName, String destinationObjectName) {
        Validate.notEmpty(fileName, "fileName can be not empty");
        Validate.notEmpty(bucketName, "bucketName can be not empty");
        Validate.notEmpty(destinationBucketName, "destinationBucketName can be not empty");
        Validate.notEmpty(destinationObjectName, "destinationObjectName can be not empty");
        OSSClient ossClient = new OSSClient(aliyunOSSProperties.getEndpoint(), aliyunOSSProperties.getAccessKeyId(), aliyunOSSProperties.getAccessKeySecret());
        // 拷贝文件。
        try {
            ossClient.copyObject(bucketName, fileName, destinationBucketName, destinationObjectName);
        } catch (OSSException oe) {
            logger.error("errorCode:{},Message:{},requestID:{}", oe.getErrorCode(), oe.getMessage(), oe.getRequestId());
            if (oe.getErrorCode().equals(NO_SUCH_KEY)) {
                throw new OSSNoSuchKeyException();
            } else if (oe.getErrorCode().equals(NO_SUCH_BUCKET)) {
                throw new OSSNoSuchBucketException();
            } else {
                throw new ApiException();
            }
        } finally {
            shutdown(ossClient);
        }
    }

    /**
     * create: 2020/2/18 12:36
     * description: 关闭oos
     *
     * @param ossClient: ossClient
     * @author niemingxin
     */
    private void shutdown(OSSClient ossClient) {
        ossClient.shutdown();
    }

    /**
     * create: 2020/2/18 12:40
     * description: 生成文件名
     *
     * @param code:             文件类型code
     * @param originalFilename: 原文件名
     * @return java.lang.String
     * @author niemingxin
     */
    private String createFileEnum(Integer code, String originalFilename) {
        String resultName;
        if (StrUtil.isNotEmpty(originalFilename)) {
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            resultName = UUID.randomUUID().toString().concat(suffix);
        } else {
            resultName = UUID.randomUUID().toString().concat(FileTypeEnum.fromCode(code));
        }
        return resultName;
    }

}


