package com.hxy.sys.entity;

import com.hxy.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 角色表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-05-03 10:07:59
 */
public class RoleEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//菜单列表
	private List<String> menuIdList;

	//组织机构列表
	private List<String> organIdList;
	
	//角色id
	private String id;
	//角色名称
	private String name;
	//角色代码
	private String code;
	//角色状态(0正常 1禁用）
	private String status;
	//角色类型
	private String roleType;

	/**
	 * 设置：角色id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：角色id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：角色名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：角色名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：角色代码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：角色代码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：角色状态(0正常 1禁用）
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：角色状态(0正常 1禁用）
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：角色类型
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	/**
	 * 获取：角色类型
	 */
	public String getRoleType() {
		return roleType;
	}

	public List<String> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		this.menuIdList = menuIdList;
	}

	public List<String> getOrganIdList() {
		return organIdList;
	}

	public void setOrganIdList(List<String> organIdList) {
		this.organIdList = organIdList;
	}
}
