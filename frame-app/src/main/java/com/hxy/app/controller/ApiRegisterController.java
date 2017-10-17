package com.hxy.app.controller;

import com.hxy.app.entity.UserApiEntity;
import com.hxy.app.service.UserApiService;
import com.hxy.base.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public Result register(UserApiEntity userApiEntity){
        userApiService.save(userApiEntity);
        return Result.ok();
    }
}
