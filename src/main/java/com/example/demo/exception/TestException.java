package com.example.demo.exception;

import com.example.demo.annotation.ResponseCode;
import com.example.demo.common.ApiException;

/**
 * description: 测试code
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
@ResponseCode("E11111")
public class TestException extends ApiException {
    public TestException() {
        super();
    }

    public TestException(String message) {
        super(message);
    }


    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestException(Throwable cause) {
        super(cause);
    }
}
