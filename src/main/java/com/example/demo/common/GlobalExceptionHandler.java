package com.example.demo.common;

import com.example.demo.annotation.ResponseCode;
import com.example.demo.constant.TestExceptionCode;
import com.example.demo.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * description: 全局异常处理
 * create: 2019/2/13 14:12
 *
 * @author NieMingXin
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<String> handle(Exception e) {

        return new Response<>(false, ResultCode.error.value(), e.getMessage(), "no data");
    }

    /**
     * 处理全局异常handler, ApiException为业务异常
     */
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public Response<String> handle(ApiException e) {
        return new Response<>(false, e.getClass().isAnnotationPresent(ResponseCode.class) ? e.getClass().getAnnotation(ResponseCode.class).value() : TestExceptionCode.TEST_CODE, e.getMessage(), null);
    }

}
