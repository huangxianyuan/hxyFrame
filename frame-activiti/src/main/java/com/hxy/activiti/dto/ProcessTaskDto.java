package com.hxy.activiti.dto;


import java.util.Date;
import java.util.List;

/**
 * 类ProcessTaskDto的功能描述:
 * 办理流程任务dto
 * @auther hxy
 * @date 2017-08-02 16:29:10
 */
public class ProcessTaskDto {
	/**
	 * 流程定义id
	 */
	private String defId;
	/**
	 * 流程实例id
	 */
	private String instanceId;

	/**
	 * 业务流程状态  1=草稿 2=审批中 3=结束
	 */
	private String status;
	/**
	 * 任务ID
	 */
	private String taskId;
	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 节点类型 1=审批 2=会签
	 */
	private String nodeType;
    /**
	 * 下一个流向办理人 以逗号隔开
	 */
    private String nextUserIds;
    /**
	 *下一个办理人名字 以逗号隔开
	 */
    private String getNextUserNames;
    /**
	 * 流程actKey
	 */
    private String actKey;
    /**
	 * 业务id
	 */
    private String busId;
    /**
	 * 业务单的单据号
	 */
    private String code;
    /**
	 * 完成任务备注
	 */
    private String remark;
	/**
	 * 是否签名，显示
	 */
	private String isSignature;
	/**
	 * 是否同意 0=是 1=否
	 */
	private String isAgree;

	/**
	 * 任务创建时间
	 */
	private Date createTime;

	/**
	 * 任务处理时间
	 */
	private Date dealTime;

	/**
	 * 流程发起人
	 */
	private String startUserName;

	/**
	 * 任务处理人
	 */
	private String dealName;

	/**
	 * 任务预处理人
	 */
	private String advanceName;

	/**
	 * 任务处理人id
	 */
	private String dealId;

	/**
	 * 业务名称
	 */
	private String busName;

	/**
	 * 下一级流程变量名,以逗号隔开
	 */
	private String varName;

	/**
	 * 下一级流程变量值,以逗号隔开
	 */
	private String varValue;


	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNextUserIds() {
		return nextUserIds;
	}

	public void setNextUserIds(String nextUserIds) {
		this.nextUserIds = nextUserIds;
	}

	public String getGetNextUserNames() {
		return getNextUserNames;
	}

	public void setGetNextUserNames(String getNextUserNames) {
		this.getNextUserNames = getNextUserNames;
	}

	public String getActKey() {
		return actKey;
	}

	public void setActKey(String actKey) {
		this.actKey = actKey;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsSignature() {
		return isSignature;
	}

	public void setIsSignature(String isSignature) {
		this.isSignature = isSignature;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarValue() {
		return varValue;
	}

	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}

	public String getAdvanceName() {
		return advanceName;
	}

	public void setAdvanceName(String advanceName) {
		this.advanceName = advanceName;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
