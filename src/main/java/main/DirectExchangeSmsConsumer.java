package main;

import com.rabbitmq.client.*;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

// 短信消费者
public class DirectExchangeSmsConsumer {
	private static final String SMS_QUEUE_NAME = "routing_sms_queue";
	private static final String EXCHANGE_NAME = "hw_exchange02";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = RabbitmqUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(SMS_QUEUE_NAME, false,
				false, false, null);
		channel.queueBind(SMS_QUEUE_NAME, EXCHANGE_NAME, "sms");
		channel.basicConsume(SMS_QUEUE_NAME, true, new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String backMsg = new String(body, StandardCharsets.UTF_8);
				System.out.println("短信消费者获取到消息："+backMsg);
			}
		});
	}
}
