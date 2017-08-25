package com.hxy.activiti.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 节点可选人
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-24 13:28:51
 */
public class ExtendActNodeuserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//节点id
	private String nodeId;
	//用户类型 1=用户 2=角色 3=组织
	private String userType;
	/**
	 * 审批名字
	 */
	private String userTitle;

	/**
	 * 审批类型名称
	 */
	private String typeName;


	/**
	 * 设置：
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：节点id
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * 获取：节点id
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * 设置：用户类型 1=用户 2=角色 3=组织
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	/**
	 * 获取：用户类型 1=用户 2=角色 3=组织
	 */
	public String getUserType() {
		return userType;
	}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
