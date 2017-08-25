package com.hxy.base.entity;

import java.util.Date;
import java.util.List;

/**
 * 类的功能描述.
 * 对业务实体做公共属性
 * @Auther hxy
 * @Date 2017/4/25
 */
public class BaseEntity {
    /**
     * 新增人
     */
    private String createId;
    /**
     * 修改者
     */
    private String updateId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 新增时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 部门(组织)ID【FK】,直接归属的组织ID
     */
    private String baid;
    /**
     * 机构ID【FK】(上级)
     */
    private String bapid;

    /**
     * 部门ids 部门数据权限
     */
    private List<String> baidList;
    /**
     * 机构ids 机构数据权限
     */
    private List<String> bapidList;

    /**
     * 部门名称
     */
    private String baName;

    /**
     * 机构名称
     */
    private String bapName;

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBaid() {
        return baid;
    }

    public void setBaid(String baid) {
        this.baid = baid;
    }

    public String getBapid() {
        return bapid;
    }

    public void setBapid(String bapid) {
        this.bapid = bapid;
    }

    public String getBaName() {
        return baName;
    }

    public void setBaName(String baName) {
        this.baName = baName;
    }

    public String getBapName() {
        return bapName;
    }

    public void setBapName(String bapName) {
        this.bapName = bapName;
    }

    public List<String> getBaidList() {
        return baidList;
    }

    public void setBaidList(List<String> baidList) {
        this.baidList = baidList;
    }

    public List<String> getBapidList() {
        return bapidList;
    }

    public void setBapidList(List<String> bapidList) {
        this.bapidList = bapidList;
    }
}
