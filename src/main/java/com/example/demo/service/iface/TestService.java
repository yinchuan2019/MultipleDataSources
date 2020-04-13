package com.example.demo.service.iface;

import com.example.demo.common.Response;
import com.example.demo.model.UserInfo;

/**
 * description: testService
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
public interface TestService {
    /**
     * create: 2020/3/12 19:17
     * description: 测试mq方法
     *
     * @return com.example.demo.common.Response<com.example.demo.model.UserInfo>
     * @author niemingxin
     */
    Response<UserInfo> findOne();

    /**
     * create: 2020/3/12 19:17
     * description: 测试动态切换数据源方法
     *
     * @return com.example.demo.common.Response<com.example.demo.model.UserInfo>
     * @author niemingxin
     */
    Response<UserInfo> find();
}

