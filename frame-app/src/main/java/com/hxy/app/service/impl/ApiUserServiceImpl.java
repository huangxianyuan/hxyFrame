package com.hxy.app.service.impl;


import com.hxy.app.dao.ApiUserDao;
import com.hxy.app.entity.ApiUserEntity;
import com.hxy.app.service.ApiUserService;
import com.hxy.base.exception.MyException;
import com.hxy.sys.dao.UserDao;
import com.hxy.sys.entity.UserEntity;
import com.hxy.sys.service.impl.UserServiceImpl;
import com.hxy.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service("userApiService")
public class ApiUserServiceImpl extends UserServiceImpl implements ApiUserService {
	@Autowired
	private ApiUserDao userDao;

	@Override
	public ApiUserEntity userInfo(String id) {
		if(StringUtils.isEmpty(id)){
			throw new MyException("用户id不能为空");
		}
		return userDao.userInfo(id);
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
			throw new MyException("手机号或密码错误");
		}
		//密码错误
		if(!user.getPassWord().equals(ShiroUtils.EncodeSalt(userEntity.getPassWord(),user.getSalt()))){
			throw new MyException("手机号或密码错误");
		}
		return user.getId();
	}
}
