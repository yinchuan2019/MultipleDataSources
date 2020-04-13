package com.example.demo.controller;

import com.example.demo.common.Response;
import com.example.demo.config.AliyunOSSProperties;
import com.example.demo.enums.FileTypeEnum;
import com.example.demo.exception.TestException;
import com.example.demo.model.UserInfo;
import com.example.demo.service.iface.TestService;
import com.example.demo.util.AliyunOSSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * description: test
 * create: 2019/2/13 14:12
 *
 * @author NieMingXin
 */
@RestController
@Api(value = "test")
@RequestMapping("demo")
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;
    @Autowired
    private AliyunOSSUtil aliyunOSSUtil;

    @ApiOperation(value = "test", notes = "testController", httpMethod = "GET")
    @GetMapping(value = "/test")
    public Response<UserInfo> findOne() {
        return testService.findOne();
    }

    @ApiOperation(value = "test1", notes = "testController", httpMethod = "GET")
    @GetMapping(value = "/test1")
    public Response<UserInfo> find() {
        return testService.find();
    }

    @ApiOperation(value = "test", notes = "testController", httpMethod = "GET")
    @GetMapping(value = "/test2")
    public void testException() {
        throw new TestException("自定义异常————————————");
    }

    @ApiOperation(value = "test", notes = "testController", httpMethod = "GET")
    @PostMapping(value = "/test3")
    public List<String> testException(@RequestParam("file") MultipartFile[] files) {
        return aliyunOSSUtil.uploadFile(files, FileTypeEnum.IMG, aliyunOSSProperties.getBucketApp(), "test", aliyunOSSProperties.getDomain());
    }


}
