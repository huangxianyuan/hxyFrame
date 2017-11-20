package com.hxy.sys.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.hxy.base.annotation.SysLog;
import com.hxy.base.utils.MD5;
import com.hxy.base.utils.Result;
import com.hxy.utils.ShiroUtils;
import com.hxy.utils.UserUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 类的功能描述.
 * 登陆控制
 * @Auther hxy
 * @Date 2017/4/28
 */
@Controller
public class LoginController extends BaseController{

    @Autowired
    private Producer producer;

    @RequestMapping("/login/captcha")
    public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = "/login/login", method = RequestMethod.POST)
    public Result login(String username, String password, String captcha,boolean isRememberMe)throws IOException {
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if(!captcha.equalsIgnoreCase(kaptcha)){
            return Result.error("验证码不正确");
        }
        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(isRememberMe);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return Result.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return Result.error("账号或密码不正确");
        }catch (LockedAccountException e) {
            return Result.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return Result.error(e.getMessage());
        }

        return Result.ok();
    }

    /**
     * 方法logOut的功能描述:
     * 退出登陆
     * @params []
     * @return java.lang.String
     * @auther hxy
     * @date 2017-05-02 14:01:23
     */
    @RequestMapping(value="logout",method = RequestMethod.GET)
    @SysLog("退出系统")
    public String logOut(){
        String loginName=UserUtils.getCurrentUser().getLoginName();

        ShiroUtils.logout();
        return "redirect:/login.html";
    }



}
