package com.hxy.demo.entity;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;



/**
 * solr 文章demo
 * 
 * @author hxy
 * @email huangxianyuan@gmail.com
 * @date 2017-09-11 15:43:10
 */
public class SolrArticleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Field
	private String id;
	//文章标题
	@Field
	private String title;
	//文章内容
	@Field
	private String content;
	//文章内容包含html
	private String contentHtml;
	//文章作者
	@Field
	private String author;
	//文章类型
	@Field
	private String type;
	//创建时间
	@Field
	private Date createTime;
	//更新时间
	@Field
	private Date updateTime;

	/**
	 * 搜索排序 例如：createTime,desc
	 */
	private String[] sorts;

	/**
	 * 封面图片
	 */
	@Field
	private String img;

	//
	private String createId;
	//
	private String updateId;

	/**
	 * 查询字段
	 */
	private String keyWords;

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
	 * 设置：文章标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：文章标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：文章内容 仅用来测试
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：文章内容 仅用来测试
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	/**
	 * 获取：
	 */
	public String getCreateId() {
		return createId;
	}
	/**
	 * 设置：
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	/**
	 * 获取：
	 */
	public String getUpdateId() {
		return updateId;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public String[] getSorts() {
		return sorts;
	}

	public void setSorts(String[] sorts) {
		this.sorts = sorts;
	}
}
