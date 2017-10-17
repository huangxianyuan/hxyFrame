package com.hxy.app.service.impl;


import com.hxy.app.dao.UserApiDao;
import com.hxy.app.entity.UserApiEntity;
import com.hxy.app.service.UserApiService;
import com.hxy.base.exception.MyException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("userApiService")
public class UserApiServiceImpl implements UserApiService {
	@Autowired
	private UserApiDao userDao;
	
	@Override
	public UserApiEntity queryObject(String userId){
		return userDao.queryObject(userId);
	}
	
	@Override
	public List<UserApiEntity> queryList(Map<String, Object> map){
		return userDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userDao.queryTotal(map);
	}
	
	@Override
	public void save(String mobile, String password){
		UserApiEntity user = new UserApiEntity();
		user.setMobile(mobile);
		user.setUsername(mobile);
		user.setPassword(DigestUtils.sha256Hex(password));
		user.setCreateTime(new Date());
		userDao.save(user);
	}
	
	@Override
	public void update(UserApiEntity user){
		userDao.update(user);
	}
	
	@Override
	public void delete(String userId){
		userDao.delete(userId);
	}
	
	@Override
	public void deleteBatch(String[] userIds){
		userDao.deleteBatch(userIds);
	}

	@Override
	public UserApiEntity queryByMobile(String mobile) {
		return userDao.queryByMobile(mobile);
	}

	@Override
	public String login(String mobile, String password) {
		UserApiEntity user = queryByMobile(mobile);

		if(user == null){
			throw new MyException("手机号或密码错误");
		}
		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(password))){
			throw new MyException("手机号或密码错误");
		}

		return user.getUserId();
	}
}
