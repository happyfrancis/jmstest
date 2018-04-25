/**
 * @program: jmstest
 * @description:
 * @author: Francis
 * @create: 2018-04-16 17:37
 **/

package com.imooc.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppConsumer {

//    private static final String url = "tcp://127.0.0.1:61616";
//    private static final String queueName="queue-test";

    private static final String url = "failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize=true";
    private static final String queueName="test1";

    public static void   main(String[] args)throws JMSException {
         //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建connection
        Connection connection = connectionFactory.createConnection();

        //3.启动连接
        connection.start();

        //4.创建会话
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标
        Destination destination = session.createQueue(queueName);

        //6.创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //7.创建一个监听器
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                TextMessage textMessage = (TextMessage) message;
//                try {
//                    System.out.println("接收消息："+textMessage.getText());
//                } catch (JMSException e){
//                    e.printStackTrace();
//                }
//            }
//        });

        consumer.setMessageListener((a) -> { //消息监听是异步的过程
            TextMessage textMessage = (TextMessage) a;
                try {
                    System.out.println("接收消息："+textMessage.getText());
                } catch (JMSException e){
                    e.printStackTrace();
                }
        });

        //8.关闭连接
        //connection.close();
        //消息监听是异步的过程 若现在关闭  消息还没有接收到就已经退出了
    }
}
