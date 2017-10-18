package com.hxy.app.controller;


import com.hxy.app.annotation.LoginRequired;
import com.hxy.app.annotation.CurrentUser;
import com.hxy.base.utils.Result;
import com.hxy.sys.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类ApiTestController的功能描述:
 * APP测试接口
 * @auther hxy
 * @date 2017-10-16 14:16:18
 */
@RestController
@RequestMapping("/app")
public class ApiTestController {

    /**
     * 获取用户信息
     */
    @LoginRequired
    @GetMapping("userInfo")
    public Result userInfo(@CurrentUser UserEntity user){
        return Result.ok().put("user", user);
    }

    /**
     * 获取用户ID
     */
    @LoginRequired
    @GetMapping("userId")
    public Result userInfo(@RequestAttribute("userId") String userId){
        return Result.ok().put("userId", userId);
    }

    /**
     * 忽略Token验证测试
     */
    @GetMapping("notToken")
    public Result notToken(){
        return Result.ok().put("msg", "无需token也能访问。。。");
    }

}
