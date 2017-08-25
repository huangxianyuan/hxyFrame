package com.hxy.base.entity;


import java.util.Date;

/**
 * 类的功能描述.
 * activiti公共属性 需要用到流程的业务，需要继承
 * @Auther hxy
 * @Date 2017/4/25
 */
public class ActivitiBaseEntity extends BaseEntity {

    /**
     * 业务流程状态  1=草稿 2=审批中 3=结束
     */
    private String status;

    /**
     * 审批结果 1为同意,2为不同意,3为审批中
     */
    private String actResult;

    /**
     * 流程发起时间
     */
    private Date startTime;

    /**
     * 流程实例id
     */
    private String instanceId;
    /**
     * 流程定义id
     */
    private String defid;
    /**
     * 流程发起人
     */
    private String startUserId;
    /**
     * 业务流程单据编号
     */
    private String code;


    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getDefid() {
        return defid;
    }

    public void setDefid(String defid) {
        this.defid = defid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActResult() {
        return actResult;
    }

    public void setActResult(String actResult) {
        this.actResult = actResult;
    }
}
