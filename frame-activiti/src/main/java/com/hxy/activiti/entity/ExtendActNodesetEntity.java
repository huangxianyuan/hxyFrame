package com.hxy.activiti.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 流程节点配置
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-24 13:28:51
 */
public class ExtendActNodesetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 审批用户类型数组
	 */
	private String[] userTypes;

	/**
	 * 审批用户id
	 */
	private String[] userIds;

	/**
	 * 连线条件集合
	 */
	private List<ExtendActNodefieldEntity> judgList;

	private String id;
	//模型id
	private String modelId;
	//流程定义id
	private String defid;
	// 流程节点id
	private String nodeId;
	//流程节点类型 =1开始节点 2=审批节点 3=分支 4=连线 5=结束节点
	private String nodeType;
	//节点行为 2 的时候 ,1=审批 2=会签
	private String nodeAction;
	//可更改的字段数据，以逗号隔开
	private String changeFiles;
	//业务回调函数
	private String callBack;

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
	 * 设置：模型id
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	/**
	 * 获取：模型id
	 */
	public String getModelId() {
		return modelId;
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
	 * 设置： 流程节点id
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * 获取： 流程节点id
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * 设置：流程节点类型 =开始节点 2=审批节点 3=分支 4=连线 5=结束节点
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	/**
	 * 获取：流程节点类型 =开始节点 2=审批节点 3=分支 4=连线 5=结束节点
	 */
	public String getNodeType() {
		return nodeType;
	}
	/**
	 * 设置：节点行为 2 的时候 ,1=审批 2=会签
	 */
	public void setNodeAction(String nodeAction) {
		this.nodeAction = nodeAction;
	}
	/**
	 * 获取：节点行为 2 的时候 ,1=审批 2=会签
	 */
	public String getNodeAction() {
		return nodeAction;
	}
	/**
	 * 设置：可更改的字段数据，以逗号隔开
	 */
	public void setChangeFiles(String changeFiles) {
		this.changeFiles = changeFiles;
	}
	/**
	 * 获取：可更改的字段数据，以逗号隔开
	 */
	public String getChangeFiles() {
		return changeFiles;
	}
	/**
	 * 设置：业务回调函数
	 */
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	/**
	 * 获取：业务回调函数
	 */
	public String getCallBack() {
		return callBack;
	}

	public List<ExtendActNodefieldEntity> getJudgList() {
		return judgList;
	}

	public void setJudgList(List<ExtendActNodefieldEntity> judgList) {
		this.judgList = judgList;
	}

	public String[] getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(String[] userTypes) {
		this.userTypes = userTypes;
	}

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
}
