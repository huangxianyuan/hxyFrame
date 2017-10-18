package com.hxy.app.controller;


import com.hxy.activiti.entity.ExtendActModelEntity;
import com.hxy.activiti.service.ActModelerService;
import com.hxy.app.annotation.LoginRequired;
import com.hxy.app.entity.ApiUserEntity;
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
@RequestMapping("/app/activiti")
public class ApiActivitiController {
    @Autowired
    private ActModelerService actModelerService;



}
