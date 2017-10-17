package com.hxy.app.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 类UserEntity的功能描述:
 * 用户
 * @auther hxy
 * @date 2017-10-16 14:16:38
 */
public class UserApiEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//用户ID
	private String userId;
	//用户名
	private String username;
	//手机号
	private String mobile;
	//密码
	transient private String password;
	//创建时间
	private Date createTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 设置：用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：用户名
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
