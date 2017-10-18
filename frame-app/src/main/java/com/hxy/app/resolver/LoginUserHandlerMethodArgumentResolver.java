package com.hxy.app.resolver;

import com.hxy.app.annotation.CurrentUser;
import com.hxy.app.entity.ApiUserEntity;
import com.hxy.app.interceptor.AuthorizationInterceptor;
import com.hxy.app.service.ApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 类LoginUserHandlerMethodArgumentResolver的功能描述:
 * 要想 @loginUser 起作用，需要编写一个配套解析器，做法是实现 spring 提供的 HandlerMethodArgumentResolver 接口。
 * @auther hxy
 * @date 2017-10-16 14:16:59
 */
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private ApiUserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(ApiUserEntity.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest webRequest, WebDataBinderFactory factory) throws Exception {
        //这一句是从 request 作用域中取出名为 userId 的属性
        Object object = webRequest.getAttribute(AuthorizationInterceptor.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
            return null;
        }
        //获取用户信息
        ApiUserEntity user = userService.userInfo((String) object);
        return user;
    }
}
