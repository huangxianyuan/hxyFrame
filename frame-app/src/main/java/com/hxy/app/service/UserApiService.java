package com.hxy.app.service;


import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.UserService;

/**
 * 类UserService的功能描述:
 * 用户
 * @auther hxy
 * @date 2017-10-16 14:17:17
 */
public interface UserApiService extends UserService{

	/**
	 * 用户登录
	 * @return          返回用户ID
	 */
	String login(UserEntity userEntity);
}
