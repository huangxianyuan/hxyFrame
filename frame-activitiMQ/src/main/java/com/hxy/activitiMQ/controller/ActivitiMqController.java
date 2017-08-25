package com.hxy.activitiMQ.controller;

import com.hxy.activitiMQ.entity.MessageBean;
import com.hxy.activitiMQ.service.ConsumerService;
import com.hxy.activitiMQ.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;


/**
 * 类MenuController的功能描述:
 * activiti 消息中间件
 * @auther hxy
 * @date 2017-06-02 15:52:52
 */
@RestController
@RequestMapping("activitiMq")
public class ActivitiMqController {
	@Autowired
	private ProducerService producerService;

	@Autowired
	private ConsumerService  consumerService;


	@RequestMapping("/send")
	public void send(){
		producerService.send("myqueue","hxy");
	}

	@RequestMapping("/sendObj")
	public void sendObj(){
        MessageBean messageBean = new MessageBean();
        messageBean.setMessage("wowowo");
        producerService.send("myqueue",messageBean);
	}

    @RequestMapping("/getMessage")
    public void getMessage(){
        try {
            consumerService.getMessageBean("myqueue");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


	
}
