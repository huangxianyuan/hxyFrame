package com.hxy.activitiMQ.service.impl;

import com.hxy.activitiMQ.entity.MessageBean;
import com.hxy.activitiMQ.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

/**
 * 类ConsumerServiceImpl的功能描述:
 * 消费者获得消息信息
 * @auther hxy
 * @date 2017-06-02 15:33:50
 */
@Service("consumerService")
public class ConsumerServiceImpl implements ConsumerService {

	protected Logger logger = LoggerFactory
			.getLogger(ConsumerServiceImpl.class);

	@Autowired
	private ConnectionFactory connectionFactory;

	private Connection connection = null;
	private MessageConsumer consumer = null;
	private Session session = null;
	private Destination destination = null;

	/**
	 * TODO 产生消费者
	 * 
	 * @param topic
	 * @return MessageConsumer 消费者
	 */
	public MessageConsumer getConsumer(String topic) {
		try {
			// 得到连接对象
			logger.debug("得到连接对象");
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 创建Queue
			destination = session.createQueue(topic);
			consumer = session.createConsumer(destination);
			return consumer;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * TODO 得到消息体
	 * 
	 * @param topic
	 * @return MessageBean
	 */
	public MessageBean getMessageBean(String topic) throws JMSException {
		MessageBean messageBean = null;
		consumer = this.getConsumer(topic);
		if (consumer != null) {
            Message receive = consumer.receive();
            System.out.println(receive);
		}

		return messageBean;
	}


	/**
	 * TODO 关闭资源
	 * 
	 * @return void
	 */
	public void close() {
		try {
			if (consumer != null)
				consumer.close();
			if (session != null)
				session.close();
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
