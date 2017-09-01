package com.hxy.activiti.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程日志扩展类
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-04 11:46:48
 */
public class ExtendActTasklogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//业务id
	private String busId;
	//流程定义id
	private String defId;
	//流程实例Id
	private String instanceId;
	//名称
	private String taskId;
	//流程任务名称
	private String taskName;
	//预处理人
	private String advanceId;
	//办理人
	private String dealId;
	//办理时间
	private Date dealTime;
	//创建时间
	private Date createTime;
	//代理人
	private String agenId;
	//审批意见
	private String appOpinion;
	//审批行为 流程任务审批结果 1=同意，2=反对，3=弃权，4=驳回 5=转办
	private String appAction;
	//是否显示签名
	private String isSign;
	//业务表更改的字段记录
	private String columns;
	/**
	 * 预处理人
	 */
	private String advanceName;

	/**
	 * 办理人
	 */
	private String dealName;

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
	 * 设置：业务id
	 */
	public void setBusId(String busId) {
		this.busId = busId;
	}
	/**
	 * 获取：业务id
	 */
	public String getBusId() {
		return busId;
	}
	/**
	 * 设置：流程定义id
	 */
	public void setDefId(String defId) {
		this.defId = defId;
	}
	/**
	 * 获取：流程定义id
	 */
	public String getDefId() {
		return defId;
	}
	/**
	 * 设置：流程实例Id
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	/**
	 * 获取：流程实例Id
	 */
	public String getInstanceId() {
		return instanceId;
	}
	/**
	 * 设置：名称
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * 获取：名称
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * 设置：流程任务名称
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	/**
	 * 获取：流程任务名称
	 */
	public String getTaskName() {
		return taskName;
	}
	/**
	 * 设置：预处理人
	 */
	public void setAdvanceId(String advanceId) {
		this.advanceId = advanceId;
	}
	/**
	 * 获取：预处理人
	 */
	public String getAdvanceId() {
		return advanceId;
	}
	/**
	 * 设置：办理人
	 */
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	/**
	 * 获取：办理人
	 */
	public String getDealId() {
		return dealId;
	}
	/**
	 * 设置：办理时间
	 */
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	/**
	 * 获取：办理时间
	 */
	public Date getDealTime() {
		return dealTime;
	}
	/**
	 * 设置：代理人
	 */
	public void setAgenId(String agenId) {
		this.agenId = agenId;
	}
	/**
	 * 获取：代理人
	 */
	public String getAgenId() {
		return agenId;
	}
	/**
	 * 设置：审批意见
	 */
	public void setAppOpinion(String appOpinion) {
		this.appOpinion = appOpinion;
	}
	/**
	 * 获取：审批意见
	 */
	public String getAppOpinion() {
		return appOpinion;
	}
	/**
	 * 设置：审批行为 同意、不同意、驳回、会签 等
	 */
	public void setAppAction(String appAction) {
		this.appAction = appAction;
	}
	/**
	 * 获取：审批行为 同意、不同意、驳回、会签 等
	 */
	public String getAppAction() {
		return appAction;
	}
	/**
	 * 设置：是否显示签名
	 */
	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	/**
	 * 获取：是否显示签名
	 */
	public String getIsSign() {
		return isSign;
	}
	/**
	 * 设置：业务表可更改的字段,逗号隔开
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}
	/**
	 * 获取：业务表可更改的字段,逗号隔开
	 */
	public String getColumns() {
		return columns;
	}

	public String getAdvanceName() {
		return advanceName;
	}

	public void setAdvanceName(String advanceName) {
		this.advanceName = advanceName;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
