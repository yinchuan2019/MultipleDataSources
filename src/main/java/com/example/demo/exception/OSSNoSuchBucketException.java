package com.example.demo.exception;

import com.example.demo.annotation.ResponseCode;
import com.example.demo.common.ApiException;


/**
 * description: oss 存储空间不存在异常
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
@ResponseCode("E22221")
public class OSSNoSuchBucketException extends ApiException {

    public OSSNoSuchBucketException() {
        super();
    }

    public OSSNoSuchBucketException(String message) {
        super(message);
    }


    public OSSNoSuchBucketException(String message, Throwable cause) {
        super(message, cause);
    }

    public OSSNoSuchBucketException(Throwable cause) {
        super(cause);
    }

}
