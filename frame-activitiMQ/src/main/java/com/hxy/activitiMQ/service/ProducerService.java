package com.hxy.activitiMQ.service;


import com.hxy.activitiMQ.entity.MessageBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/** 
 * @Title: ProducerService 
 * @Description: 生产者
 * @Copyright: Copyright (c) 2015-2020
 * @Company:CCS
 * @date 2015年9月15日 上午10:51:20 
 *  
*/ 
public interface ProducerService extends Serializable{
	
    /**
     * TODO 发送string 消息
     * @param topicName
     * @param message
     * @return  boolean
     */
	public boolean send(String topicName, String message);
	
	/**
     * TODO 发送object 消息
     * @param topicName
     * @param obj
     * @return  boolean
     */
	public boolean send(String topicName, MessageBean obj);
	
	/**
     * TODO 发送批量消息
     * @param msgMapList
     * @return  boolean
     */
	public boolean send(List<Map<String, Object>> msgMapList);
	
}
