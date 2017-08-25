package com.hxy.activiti.entity;

import com.hxy.base.entity.ActivitiBaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;



/**
 * 流程模板扩展表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-07-14 11:02:01
 */
public class ExtendActModelEntity extends ActivitiBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private String id;
	//版本号
	private Integer actVersion;
	//关联的 业务表 ID
	@NotBlank(message = "关联业务不能为空")
	private String extendActBusinessId;
	//activiti中的部署表id
	private String deploymentId;
	//描述
	private String description;
	//activiti中的模型表id
	private String modelId;
	//名称
	@NotBlank(message = "模型名称不能为空")
	private String name;
	//发布状态 0:已发布 1：未发布
	private String status;
	//业务名称
	private String businessName;
	/**
	 * 流程key
	 */
	private String actKey;

	/**
	 * 设置：主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：版本号
	 */
	public void setActVersion(Integer actVersion) {
		this.actVersion = actVersion;
	}
	/**
	 * 获取：版本号
	 */
	public Integer getActVersion() {
		return actVersion;
	}
	/**
	 * 设置：关联的 业务表 ID
	 */
	public void setExtendActBusinessId(String extendActBusinessId) {
		this.extendActBusinessId = extendActBusinessId;
	}
	/**
	 * 获取：关联的 业务表 ID
	 */
	public String getExtendActBusinessId() {
		return extendActBusinessId;
	}
	/**
	 * 设置：activiti中的部署表id
	 */
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	/**
	 * 获取：activiti中的部署表id
	 */
	public String getDeploymentId() {
		return deploymentId;
	}
	/**
	 * 设置：描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：描述
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：activiti中的模型表id
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	/**
	 * 获取：activiti中的模型表id
	 */
	public String getModelId() {
		return modelId;
	}
	/**
	 * 设置：名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：发布状态 1:已发布 2：未发布
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：发布状态 1:已发布 2：未发布
	 */
	public String getStatus() {
		return status;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getActKey() {
		return actKey;
	}

	public void setActKey(String actKey) {
		this.actKey = actKey;
	}


}
