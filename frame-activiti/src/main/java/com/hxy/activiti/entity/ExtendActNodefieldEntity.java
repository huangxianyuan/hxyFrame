package com.hxy.activiti.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 流程节点对应的字段条件
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-24 13:28:51
 */
public class ExtendActNodefieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//节点id
	private String nodeId;
	//字段名称
	private String fieldName;
	//字段类型  1=可写(可写的也能读) 2=参与连线判断
	private String fieldType;
	//判断规则 "==",">=","<=",">","<"
	private String rule;
	//条件值
	private String fieldVal;
	//el表达式 运算符 &&=并且，||=或
	private String elOperator;
	//同一节点 条件 排序
	private String sort;

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
	 * 设置：字段名称
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * 获取：字段名称
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * 设置：字段类型  1=可写(可写的也能读) 2=参与连线判断
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	/**
	 * 获取：字段类型  1=可写(可写的也能读) 2=参与连线判断
	 */
	public String getFieldType() {
		return fieldType;
	}
	/**
	 * 设置：判断规则 "==",">=","<=",">","<"
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}
	/**
	 * 获取：判断规则 "==",">=","<=",">","<"
	 */
	public String getRule() {
		return rule;
	}

	public String getFieldVal() {
		return fieldVal;
	}

	public void setFieldVal(String fieldVal) {
		this.fieldVal = fieldVal;
	}

	public String getElOperator() {
		return elOperator;
	}

	public void setElOperator(String elOperator) {
		this.elOperator = elOperator;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
}
