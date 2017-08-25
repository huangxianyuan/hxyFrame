package com.hxy.activitiMQ.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** 
  * @Title: MessageBean 
  * @Description: 消息对象
  * @Copyright: Copyright (c) 2015-2020
  * @Company:CCS
  * @author enzhi 
  * @date 2015年9月15日 上午10:51:20 
  *  
*/ 
public class MessageBean implements Serializable {
	private static final long serialVersionUID = 6076863653513044393L;
	public static final String APP_MES_TYPE = "2";
	public static final String PROCESS_MES_TYPE = "2";//应用和流程消息共用2
	public static final String INTERFACE_MES_TYPE = "3";
	public static final String Proj_Status = "4";
	private String fromId;// 生产者id
	private String toId;// 消费者id
	private String fromName;// 生产者名称
	private String toName;// 消费者名称
	private String title;// 标题
	private String message;// 消息内容
	private Date sendtime;// 发送时间
	private String classify;// 分类 应用分类 ，项目，流程，接口
	private String claType;//子类型
	private Map<String, Object> body = null;// 消息体

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("title:").append(title).append("\n");
		sb.append("message:").append(message).append("\n");
		sb.append("toId:").append(toId).append("\n");
		sb.append("toName:").append(toName).append("\n");
		sb.append("classify:").append(classify).append("\n");
		return super.toString();
	}
	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getClaType() {
		return claType;
	}

	public void setClaType(String claType) {
		this.claType = claType;
	}

}
