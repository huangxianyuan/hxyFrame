import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/6/5
 */

public class ActivitiMQTest {

    // 发送消息
    @Test
    public void oneSendMessage() throws Exception {
        // 1） 创建消息工厂，ActiveMQConnectionFactory，需要传递参数：协议，地址，端口。
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 2） 从工厂中获取连接
        Connection connection = connectionFactory.createConnection();
        // 3） 开启连接
        connection.start();
        // 4） 从连接中获取Session
        //第一个参数：消息事务，如果第一个参数是true，第二个参数将会被忽略
        //第二个参数：消息应答模式，自动应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5） 从Session中获取消息目的地
        //创建一个消息目的地，给消息目的起一个名称：myqueue
        //相当域在ActiveMQ服务中开辟了名称为myqueue一块空间，消息发送myqueue这块空间中。
        //消息有两种模式：点对点（queue），发布定阅模式：（Topic）
        Queue queue = session.createQueue("test1");
        // 6） 创建消息发生者
        MessageProducer producer = session.createProducer(queue);
        // 7） 发送消息
        TextMessage message = new ActiveMQTextMessage();
        message.setText("我们都是中国人kaaa");
        producer.send(message);
        // 8） 关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    //异步模式接受消息
    @Test
    public void receiveMessageSycr() throws Exception{
        // 1） 创建消息工厂，ActiveMQConnectionFactory，需要传递参数：协议，地址，端口。
        ConnectionFactory connectionFactory = new
                ActiveMQConnectionFactory(
                "tcp://127.0.0.1:61616");
        // 2） 从工厂中获取连接
        Connection connection =
                connectionFactory.createConnection();
        // 3） 开启连接
        connection.start();
        // 4） 从连接中获取Session
        //第一个参数：消息事务，如果第一个参数是true，第二个参数将会被忽略
        //第二个参数：消息应答模式，自动应答模式
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        // 5） 从Session中获取消息目的地
        //创建一个消息目的地，给消息目的起一个名称：myqueue
        //相当域在ActiveMQ服务中开辟了名称为myqueue一块空间，消息发送myqueue这块空间中。
        //消息有两种模式：点对点（queue），发布定阅模式：（Topic）
        Queue queue = session.createQueue("test1");
        //6）创建消息接受者
        MessageConsumer consumer = session.createConsumer(queue);
        //异步模式接受使用监听器接受消息
        consumer.setMessageListener(new MessageListener(){
            @Override
            public void onMessage(Message message) {
                if(message instanceof TextMessage){
                    TextMessage tm = (TextMessage) message;
                    try {
                        //打印消息
                        System.out.println(tm.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //发布订阅模式，发送消息
    @Test
    public void oneSendMessageTopic() throws Exception {
        // 1） 创建消息工厂，ActiveMQConnectionFactory，需要传递参数：协议，地址，端口。
        ConnectionFactory connectionFactory = new
                ActiveMQConnectionFactory(
                "tcp://127.0.0.1:61616");
        // 2） 从工厂中获取连接
        Connection connection = connectionFactory.createConnection();
        // 3） 开启连接
        connection.start();
        // 4） 从连接中获取Session
        //第一个参数：消息事务，如果第一个参数是true，第二个参数将会被忽略
        //第二个参数：消息应答模式，自动应答模式
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        // 5） 从Session中获取消息目的地
        //创建一个消息目的地，给消息目的起一个名称：mytopic
        //相当在ActiveMQ服务中开辟了名称为mytopic一块空间，消息发送mytopic这块空间中。
        //消息有两种模式：发布定阅模式：（Topic）,点对点（queue）
        Topic topic = session.createTopic("mytopic");
        // 6） 创建消息发生者
        MessageProducer producer = session.createProducer(topic);
        // 7） 发送消息
        TextMessage message = new ActiveMQTextMessage();
        message.setText("我们都是mytopic");
        producer.send(message);
        // 8） 关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    //异步模式接受Topic消息
    @Test
    public void receiveMessageSycrTopic() throws Exception{
        // 1） 创建消息工厂，ActiveMQConnectionFactory，需要传递参数：协议，地址，端口。
        ConnectionFactory connectionFactory = new
                ActiveMQConnectionFactory(
                "tcp://127.0.0.1:61616");
        // 2） 从工厂中获取连接
        Connection connection =
                connectionFactory.createConnection();
        // 3） 开启连接
        connection.start();
        // 4） 从连接中获取Session
        //第一个参数：消息事务，如果第一个参数是true，第二个参数将会被忽略
        //第二个参数：消息应答模式，自动应答模式
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        // 5） 从Session中获取消息目的地
        //创建一个消息目的地，给消息目的起一个名称：myqueue
        //相当域在ActiveMQ服务中开辟了名称为myqueue一块空间，消息发送myqueue这块空间中。
        //消息有两种模式：点对点（queue），发布定阅模式：（Topic）
        Topic topic = session.createTopic("mytopic");
        //6）创建消息接受者
        MessageConsumer consumer = session.createConsumer(topic);
        //异步模式接受使用监听器接受消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message)  {
                if(message instanceof TextMessage){
                    TextMessage tm = (TextMessage) message;
                    try {
                        //打印消息
                        System.out.println(tm.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
