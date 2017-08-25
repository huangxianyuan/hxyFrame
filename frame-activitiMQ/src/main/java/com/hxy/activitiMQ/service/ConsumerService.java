package com.hxy.activitiMQ.service;


import com.hxy.activitiMQ.entity.MessageBean;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import java.io.Serializable;

/**
 * @Title: ConsumerService
 * @Description: TODO消费者获得消息信息
 * @Copyright: Copyright (c) 2015-2020
 * @Company:CCS
 * @author enzhi
 * @date 2015年8月7日 上午10:27:41
 * 
 */
public interface ConsumerService extends Serializable{

	/**
	 * @Description 生产消费者
	 * @param topic
	 * @return MessageConsumer
	 */
	public MessageConsumer getConsumer(String topic);

	/**
	 * @Description 获取消息
	 * @param topic
	 * @return MessageBean
	 */
	public MessageBean getMessageBean(String topic) throws JMSException;

	/**
	 * TODO 关闭资源
	 * 
	 * @return void
	 */
	public void close();

}
