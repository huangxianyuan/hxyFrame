package com.hxy.sys.entity;

import com.hxy.base.entity.BaseEntity;
import org.aspectj.apache.bcel.classfile.Code;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 系统数据字典
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-14 13:42:42
 */
public class CodeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//码值唯一标识, 例如，SEX、SEX_1、SEX_2
	private String mark;
	//码值 的数字表示，例如：1，2，3。。。。。、sex
	private String value;
	//码值的中文表示， 例如：是、否      、性别
	private String name;
	//1：目录 2：字典码
	private String type;

	private String parentId;
	//在同一级节点中的序号
	private String sort;
	//父级名字
	private String parentName;
	//是否展开
	private String open;
	//子字典 逗号隔开
	private String childs;
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
	 * 设置：码值唯一标识, 例如，SEX、SEX_1、SEX_2
	 */
	public void setMark(String mark) {
		this.mark = mark;
	}
	/**
	 * 获取：码值唯一标识, 例如，SEX、SEX_1、SEX_2
	 */
	public String getMark() {
		return mark;
	}
	/**
	 * 设置：码值 的数字表示，例如：1，2，3。。。。。、sex
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：码值 的数字表示，例如：1，2，3。。。。。、sex
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置：码值的中文表示， 例如：是、否      、性别
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：码值的中文表示， 例如：是、否      、性别
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：
	 */
	public String getParentId() {
		return parentId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getChilds() {
		return childs;
	}

	public void setChilds(String childs) {
		this.childs = childs;
	}

}
