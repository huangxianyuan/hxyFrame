package com.hxy.sys.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 类SysOssEntity的功能描述:
 * 文件上传
 * @auther hxy
 * @date 2017-08-25 16:17:47
 */
public class SysOssEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//URL地址
	private String url;
	//创建时间
	private Date createDate;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：URL地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取：URL地址
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
}
