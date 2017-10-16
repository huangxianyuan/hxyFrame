package com.hxy.app.controller;

import com.hxy.app.service.UserApiService;
import com.hxy.base.utils.Result;
import com.hxy.base.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类ApiRegisterController的功能描述:
 * 注册
 * @auther hxy
 * @date 2017-10-16 14:16:01
 */
@Controller
@RequestMapping("/app")
public class ApiRegisterController {
    @Autowired
    private UserApiService userApiService;

    /**
     * 注册
     */
    @RequestMapping(value = "register",method = RequestMethod.POST)
    public Result register(String mobile, String password){
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");

        userApiService.save(mobile, password);

        return Result.ok();
    }
}
