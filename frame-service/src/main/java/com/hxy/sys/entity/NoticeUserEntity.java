package com.hxy.sys.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 通知和用户关系表
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-31 15:58:58
 */
public class NoticeUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//用户id
	private String userId;
	//
	private String noticeId;
	//0已读 1未读
	private String status;
	//备用字段
	private String remark;

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
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：
	 */
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	/**
	 * 获取：
	 */
	public String getNoticeId() {
		return noticeId;
	}
	/**
	 * 设置：0已读 1未读
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：0已读 1未读
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：备用字段
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备用字段
	 */
	public String getRemark() {
		return remark;
	}
}
