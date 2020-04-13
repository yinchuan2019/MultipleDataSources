package com.example.demo.enums;

/**
 * description:返回结果code枚举
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
public enum ResultCode {
    /**
     * 业务正常code
     */
    FORMAL("0", "业务正常"),
    /**
     * 全局异常code
     */
    error("E00000", "全局异常~~~~~");

    private String value;
    private String message;

    ResultCode(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String value() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
