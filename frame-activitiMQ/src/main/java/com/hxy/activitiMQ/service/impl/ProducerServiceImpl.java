package com.hxy.activitiMQ.service.impl;

import com.hxy.activitiMQ.entity.MessageBean;
import com.hxy.activitiMQ.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Title: ProducerServiceImpl
 * @Description: 生产者
 * @Copyright: Copyright (c) 2015-2020
 * @Company:CCS
 * @date 2015年9月15日 上午10:51:20
 * 
 */
@Service("producerService")
public class ProducerServiceImpl implements ProducerService {
	private Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

	@Autowired
	private ConnectionFactory connectionFactory;
	// Connection ：JMS客户端到JMS Provider的连接
	private Connection connection = null;
	// Session：一个发送或接收消息的线程
	private Session session;
	// Destination ：消息的目的地;消息发送给谁.
	private Destination destination = null;
	// MessageProducer：消息发送者
	private MessageProducer producer;

	/**
	 * TODO 发送string 消息
	 * 
	 * @param topicName
	 * @param message
	 * @return boolean
	 */
	@Override
	public boolean send(String topicName, String message) {
		if (createFactory(topicName)) {
			try {
				logger.info("发送消息：" + message);
				TextMessage messagex = session.createTextMessage(message);
				// System.out.println("发送消息：" + message);
				producer.send(messagex);
			} catch (Exception e) {
				return false;
			} finally {
				try {
					close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * TODO 发送object 消息
	 * 
	 * @param topicName
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean send(String topicName, MessageBean obj) {
		if (createFactory(topicName)) {
			try {
				ObjectMessage msg = session
						.createObjectMessage((Serializable) obj);
				producer.send(msg);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	private boolean createFactory(String topicName) {
		try {
			logger.info("构造从工厂得到连接对象");
			connection = connectionFactory.createConnection();
			logger.info("启动");
			connection.start();
			logger.info("获取操作连接");
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			logger.info(" 获取session，FirstQueue是一个服务器的queue ");
			destination = session.createQueue(topicName);
			// 得到消息生成者【发送者】
			producer = session.createProducer(destination);
			// 设置不持久化
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			// session.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("构造从工厂失败:" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * TODO 关闭资源
	 * 
	 * @throws JMSException
	 */
	private void close() throws JMSException {
		if (producer != null)

			producer.close();
		if (session != null)
			session.close();
		if (connection != null)
			connection.close();
	}

	/**
	 * TODO 发送批量消息 send
	 * 
	 * @return boolean
	 */
	@Override
	public boolean send(List<Map<String, Object>> msgMapList) {
		if (msgMapList != null && msgMapList.size() > 0) {
			for (Map<String, Object> msgMap : msgMapList) {
				String topic = msgMap.get("topicName") == null ? "" : msgMap
						.get("topicName").toString();
				if (!"".equals(topic)) {
					MessageBean messageBean = (MessageBean) msgMap
							.get("messageBean");
					this.send(topic, messageBean);
				}
			}
			return true;
		}
		return false;
	}

}