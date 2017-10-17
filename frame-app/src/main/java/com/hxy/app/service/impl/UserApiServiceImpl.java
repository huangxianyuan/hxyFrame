package com.hxy.app.service.impl;


import com.hxy.app.dao.UserApiDao;
import com.hxy.app.entity.UserApiEntity;
import com.hxy.app.service.UserApiService;
import com.hxy.base.exception.MyException;
import com.hxy.sys.dao.UserDao;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.impl.UserServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("userApiService")
public class UserApiServiceImpl extends UserServiceImpl implements UserApiService {
	@Autowired
	private UserDao userDao;

	@Override
	public String login(UserEntity userEntity) {
		if(StringUtils.isEmpty(userEntity.getLoginName())){
			throw new MyException("登陆用户名不能为空!");
		}
		UserEntity user = queryByLoginName(userEntity.getLoginName());
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
