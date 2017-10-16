package com.hxy.app.service;



import com.hxy.app.entity.UserApiEntity;

import java.util.List;
import java.util.Map;

/**
 * 类UserService的功能描述:
 * 用户
 * @auther hxy
 * @date 2017-10-16 14:17:17
 */
public interface UserApiService {

	UserApiEntity queryObject(String userId);
	
	List<UserApiEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(String mobile, String password);
	
	void update(UserApiEntity user);
	
	void delete(String userId);
	
	void deleteBatch(String[] userIds);

	UserApiEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param mobile    手机号
	 * @param password  密码
	 * @return          返回用户ID
	 */
	String login(String mobile, String password);
}
