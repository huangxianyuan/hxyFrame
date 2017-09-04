package com.hxy.demo.entity;

import com.hxy.activiti.annotation.ActField;
import com.hxy.activiti.annotation.ActTable;
import com.hxy.base.entity.ActivitiBaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 类的功能描述.
 * 请假demo
 * @Auther hxy
 * @Date 2017/7/27
 */
@ActTable(tableName = "leaveaply",pkName="id")
public class LeaveEntity extends ActivitiBaseEntity{

    private String id;

    private String userId;

    @ActField(name = "请假标题")
    @NotEmpty(message = "请假标题不能为空")
    private String title;

    @NotEmpty(message = "请假天数不能为空")
    @ActField(name = "请假天数",isJudg = true)
    private int day;

    /**
     * 请假原因
     */
    @NotEmpty(message = "请假原因不能为空")
    private String leavewhy;

    /**
     * 请假人姓名
     */
    private String leaveUser;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getLeavewhy() {
        return leavewhy;
    }

    public void setLeavewhy(String leavewhy) {
        this.leavewhy = leavewhy;
    }

    public String getLeaveUser() {
        return leaveUser;
    }

    public void setLeaveUser(String leaveUser) {
        this.leaveUser = leaveUser;
    }
}
