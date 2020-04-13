package com.example.demo.common;

import com.example.demo.annotation.ResponseCode;


/**
 * description:业务异常基类
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
@ResponseCode
public class ApiException extends RuntimeException {
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }


    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
