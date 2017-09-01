package com.hxy.sys.entity;

import com.hxy.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-08-31 15:59:09
 */
public class NoticeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String id;
	//通知内容
	private String context;
	//通知标题
	private String title;
	//通知来源 1=普通通知（人工发起） 2=流程通知
	private String soucre;
	//通知状态 0=已发布 1=草稿 
	private String status;
	//是否紧急 0是1否
	private String isUrgent;
	//发布时间
	private Date releaseTimee;

	/**
	 * 通知用户id 查询用
	 */
	private String userId;

	/**
	 * 通知用户姓名 显示用
	 */
	private String userName;

	/**
	 * 阅读状态
	 */
	private String showStatus;

	/**
	 * 创建人姓名 显示用
	 */
	private String createName;

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
	 * 设置：通知内容
	 */
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * 获取：通知内容
	 */
	public String getContext() {
		return context;
	}
	/**
	 * 设置：通知标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：通知标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：通知来源 1=普通通知（人工发起） 2=流程通知
	 */
	public void setSoucre(String soucre) {
		this.soucre = soucre;
	}
	/**
	 * 获取：通知来源 1=普通通知（人工发起） 2=流程通知
	 */
	public String getSoucre() {
		return soucre;
	}
	/**
	 * 设置：通知状态 0=已发布 1=草稿 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：通知状态 0=已发布 1=草稿 
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：是否紧急 0是1否
	 */
	public void setIsUrgent(String isUrgent) {
		this.isUrgent = isUrgent;
	}
	/**
	 * 获取：是否紧急 0是1否
	 */
	public String getIsUrgent() {
		return isUrgent;
	}
	/**
	 * 设置：发布时间
	 */
	public void setReleaseTimee(Date releaseTimee) {
		this.releaseTimee = releaseTimee;
	}
	/**
	 * 获取：发布时间
	 */
	public Date getReleaseTimee() {
		return releaseTimee;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}
}
