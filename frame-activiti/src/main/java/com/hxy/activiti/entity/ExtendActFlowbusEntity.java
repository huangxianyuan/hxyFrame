package com.hxy.activiti.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 业务流程关系表与activitiBaseEntity中字段一样
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-04 13:56:50
 */
public class ExtendActFlowbusEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//业务ID
	private String busId;
	//业务流程状态  1=草稿 2=审批中 3=结束
	private String status;
	//流程发起时间
	private Date startTime;
	//流程实例id
	private String instanceId;
	//流程定义id
	private String defid;
	//流程发起人
	private String startUserId;
	//业务流程单据编号
	private String code;
	//流程key
	private String actKey;
	//业务表名
	private String tableName;

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
	 * 设置：业务ID
	 */
	public void setBusId(String busId) {
		this.busId = busId;
	}
	/**
	 * 获取：业务ID
	 */
	public String getBusId() {
		return busId;
	}
	/**
	 * 设置：业务流程状态  1=草稿 2=审批中 3=结束
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：业务流程状态  1=草稿 2=审批中 3=结束
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：流程发起时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：流程发起时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：流程实例id
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	/**
	 * 获取：流程实例id
	 */
	public String getInstanceId() {
		return instanceId;
	}
	/**
	 * 设置：流程定义id
	 */
	public void setDefid(String defid) {
		this.defid = defid;
	}
	/**
	 * 获取：流程定义id
	 */
	public String getDefid() {
		return defid;
	}
	/**
	 * 设置：流程发起人
	 */
	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}
	/**
	 * 获取：流程发起人
	 */
	public String getStartUserId() {
		return startUserId;
	}
	/**
	 * 设置：业务流程单据编号
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：业务流程单据编号
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：流程key
	 */
	public void setActKey(String actKey) {
		this.actKey = actKey;
	}
	/**
	 * 获取：流程key
	 */
	public String getActKey() {
		return actKey;
	}
	/**
	 * 设置：业务表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 获取：业务表名
	 */
	public String getTableName() {
		return tableName;
	}
}
