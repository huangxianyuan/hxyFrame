package com.hxy.app.annotation;

import java.lang.annotation.*;

/**
 * 类Login的功能描述:
 * app登录效验
 * @auther hxy
 * @date 2017-10-16 14:14:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
