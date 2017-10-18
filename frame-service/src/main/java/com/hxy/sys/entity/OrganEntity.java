package com.hxy.sys.entity;

import com.hxy.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;



/**
 * 组织表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-14 13:42:42
 */
public class OrganEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//该部门的归属机构ID ，只有当是部门的时候才生效
	private String bapid;
	//结点类型：0=根节点 ，1=机构，2=部门
	private String type;
	//编号
	private String code;
	//节点的名字
	private String name;
	//节点的父节点
	private String parentId;
	//是否删除 0 是 1 否
	private String isDel;
	//系统标识，32*10+9 最多支持10级节点。用户具体一批数据的控制
	private String sysmark;
	//在同一级节点中的序号
	private String sort;
	//是否展开 0 是 1 否
	private String open;
	//图标
	private String icon;

	private String parentName;

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
	 * 设置：该部门的归属机构ID ，只有当是部门的时候才生效
	 */
	public void setBapid(String bapid) {
		this.bapid = bapid;
	}
	/**
	 * 获取：该部门的归属机构ID ，只有当是部门的时候才生效
	 */
	public String getBapid() {
		return bapid;
	}
	/**
	 * 设置：结点类型：0=根节点 ，1=机构，2=部门
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：结点类型：0=根节点 ，1=机构，2=部门
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：编号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：编号
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：节点的名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：节点的名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：节点的父节点
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：节点的父节点
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：是否删除 0 是 1 否
	 */
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	/**
	 * 获取：是否删除 0 是 1 否
	 */
	public String getIsDel() {
		return isDel;
	}
	/**
	 * 设置：系统标识，32*10+9 最多支持10级节点。用户具体一批数据的控制
	 */
	public void setSysmark(String sysmark) {
		this.sysmark = sysmark;
	}
	/**
	 * 获取：系统标识，32*10+9 最多支持10级节点。用户具体一批数据的控制
	 */
	public String getSysmark() {
		return sysmark;
	}
	/**
	 * 设置：在同一级节点中的序号
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * 获取：在同一级节点中的序号
	 */
	public String getSort() {
		return sort;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
