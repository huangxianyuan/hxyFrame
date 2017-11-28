package com.hxy.sys.entity;

import java.io.Serializable;


/**
 * 用户角色关系表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 10:07:59
 */
public class UserRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//用户id
	private String userId;
	//角色id
	private String roleId;

	/**
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：角色id
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * 获取：角色id
	 */
	public String getRoleId() {
		return roleId;
	}
}
