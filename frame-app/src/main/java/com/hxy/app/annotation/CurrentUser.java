package com.hxy.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类LoginUser的功能描述:
 * 在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的User对象
 * @auther hxy
 * @date 2017-10-16 14:15:11
 */
// 可用在方法的参数上
@Target(ElementType.PARAMETER)
// 运行时有效
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

}
