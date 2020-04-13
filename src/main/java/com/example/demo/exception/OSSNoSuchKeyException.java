package com.example.demo.exception;

import com.example.demo.annotation.ResponseCode;
import com.example.demo.common.ApiException;


/**
 * description: oss文件不存在异常
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
@ResponseCode("E22222")
public class OSSNoSuchKeyException extends ApiException {

    public OSSNoSuchKeyException() {
        super();
    }

    public OSSNoSuchKeyException(String message) {
        super(message);
    }


    public OSSNoSuchKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public OSSNoSuchKeyException(Throwable cause) {
        super(cause);
    }

}
