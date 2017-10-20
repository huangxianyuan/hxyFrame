package com.hxy.app.controller;


import com.hxy.activiti.entity.ExtendActModelEntity;
import com.hxy.activiti.service.ActModelerService;
import com.hxy.app.annotation.CurrentUser;
import com.hxy.app.annotation.LoginRequired;
import com.hxy.app.entity.ApiUserEntity;
import com.hxy.app.service.ApiActivitiService;
import com.hxy.app.service.ApiNoticeService;
import com.hxy.app.service.ApiUserService;
import com.hxy.base.annotation.SysLog;
import com.hxy.base.page.Page;
import com.hxy.base.utils.Result;
import com.hxy.base.utils.Utils;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.NoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 类ApiLoginController的功能描述:
 * APP登录授权
 * @auther hxy
 * @date 2017-10-16 14:15:39
 */
@Controller
@RequestMapping("/app/user")
public class ApiUserController {
    @Autowired
    private ApiUserService userApiService;
    @Autowired
    private ActModelerService actModelerService;
    @Autowired
    private ApiNoticeService apiNoticeService;
    @Autowired
    private ApiActivitiService apiActivitiService;

    /**
     * 用户信息
     */
    @RequestMapping(value = "userInfo",method = RequestMethod.POST)
    @LoginRequired
    @ResponseBody
    public Result info(@CurrentUser ApiUserEntity apiUserEntity){
        ApiUserEntity user = userApiService.userInfo(apiUserEntity.getId());
        //待办条数
        int myUpcomingCount = apiActivitiService.myUpcomingCount(apiUserEntity.getId());
        //我的通知条数
        int myNoticeCount = apiNoticeService.myNoticeCount(apiUserEntity.getId());
        user.setMyUpcomingCount(myUpcomingCount);
        user.setMyNoticeCount(myNoticeCount);
        return Result.ok().put("user", user);
    }

    /**
     * 修改用户信息
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @LoginRequired
    @ResponseBody
    public Result update(UserEntity user){
        userApiService.update(user);
        return Result.ok();
    }

    /**
     * 修改当前用户密码
     */
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    @LoginRequired
    @ResponseBody
    public Result updatePassword(UserEntity newUser,@CurrentUser ApiUserEntity apiUserEntity){
        int i = userApiService.updatePassword(newUser,apiUserEntity);
        if(i<1){
            return Result.error("更改密码失败");
        }
        return Result.ok("更改密码成功");
    }


}
