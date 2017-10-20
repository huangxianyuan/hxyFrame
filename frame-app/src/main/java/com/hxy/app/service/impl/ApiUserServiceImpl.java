package com.hxy.app.service.impl;


import com.hxy.app.dao.ApiUserDao;
import com.hxy.app.entity.ApiUserEntity;
import com.hxy.app.service.ApiUserService;
import com.hxy.base.common.Constant;
import com.hxy.base.exception.MyException;
import com.hxy.sys.dao.UserDao;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.impl.UserServiceImpl;
import com.hxy.utils.ShiroUtils;
import com.hxy.utils.UserUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Service("userApiService")
public class ApiUserServiceImpl extends UserServiceImpl implements ApiUserService {
	@Autowired
	private ApiUserDao apiUserDao;

	@Autowired
	private UserDao userDao;

	@Override
	public ApiUserEntity userInfo(String id) {
		if(StringUtils.isEmpty(id)){
			throw new MyException("用户id不能为空");
		}
		return apiUserDao.userInfo(id);
	}

	@Override
	public String login(UserEntity userEntity) {
		if(StringUtils.isEmpty(userEntity.getLoginName())){
			throw new MyException("登陆用户名不能为空!");
		}
		if(StringUtils.isEmpty(userEntity.getPassWord())){
			throw new MyException("登陆密码不能为空!");
		}
		UserEntity user = queryByLoginName(userEntity.getLoginName());
		if(user == null){
			throw new MyException("登陆用户名或密码错误");
		}
		//密码错误
		if(!user.getPassWord().equals(ShiroUtils.EncodeSalt(userEntity.getPassWord(),user.getSalt()))){
			throw new MyException("登陆用户名或密码错误");
		}
		return user.getId();
	}

	@Override
	public int updatePassword(UserEntity newUser,UserEntity oldUser) {
		if(newUser == null){
			throw new MyException("用户信息不能为空!");
		}
		if(com.hxy.base.utils.StringUtils.isEmpty(newUser.getNewPassWord())){
			throw new MyException("新密码不能为空");
		}
		String newPassWord = ShiroUtils.EncodeSalt(newUser.getPassWord(),oldUser.getSalt());
		if(Constant.SUPERR_USER.equals(oldUser.getId())){
			throw new MyException("不能修改超级管理员密码!");
		}
		if(!newPassWord.equals(oldUser.getPassWord())){
			throw new MyException("密码不正确");
		}
		Map<String,Object> params = new HashMap<>();
		//生成salt
		String salt = RandomStringUtils.randomAlphanumeric(20);
		params.put("id",oldUser.getId());
		params.put("salt",salt);
		params.put("passWord",ShiroUtils.EncodeSalt(newUser.getNewPassWord(),salt));
		return userDao.updatePassword(params);
	}
}
