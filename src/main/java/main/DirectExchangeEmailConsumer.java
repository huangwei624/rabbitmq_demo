package main;

import com.rabbitmq.client.*;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

// 邮件消费者
public class DirectExchangeEmailConsumer {
	
	private static final String EMAIL_QUEUE_NAME = "routing_email_queue";
	private static final String EXCHANGE_NAME = "hw_exchange02";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = RabbitmqUtil.getConnection();
		Channel channel = connection.createChannel();
		// 创建队列
		channel.queueDeclare(EMAIL_QUEUE_NAME, false,
				false, false, null);
		// 队列绑定交换机
		channel.queueBind(EMAIL_QUEUE_NAME, EXCHANGE_NAME, "email");
		channel.basicConsume(EMAIL_QUEUE_NAME, true, new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String backMsg = new String(body, StandardCharsets.UTF_8);
				System.out.println("邮件消费者获取到消息："+backMsg);
			}
		});
	}
}
