package com.example.demo.service.impl;

import com.example.demo.annotation.DataSource;
import com.example.demo.common.DataSourceType;
import com.example.demo.common.Response;
import com.example.demo.dao.mapper.UserInfoMapper;
import com.example.demo.model.UserInfo;
import com.example.demo.service.iface.TestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: TestServiceImpl
 * create: 2020/3/12 18:50
 *
 * @author NieMingXin
 * @version 1.0
 */
@Service
public class TestServiceImpl implements TestService {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Response<UserInfo> findOne() {
        UserInfo userInfo = userInfoMapper.selectById(1L);
        //调用mq
        rabbitTemplate.convertAndSend("directExchange", "directTest", userInfo);
        return new Response<>(true, "200", "yes", userInfo);
    }

    @Override
    @DataSource(DataSourceType.MASTER)
    public Response<UserInfo> find() {
        UserInfo userInfo = userInfoMapper.selectById(1L);
        Response<UserInfo> response = new Response<>();
        response.setResult(userInfo);
        response.setMessage("yes");
        return response;
    }
}
