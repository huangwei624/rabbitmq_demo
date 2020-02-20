package main;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 模拟生产者向 direct交换机中发送消息, 发消息时设置路由键，routing key
// 1. 消息队列绑定交换机的时候设置路由键，
// 2. 生产者发送消息时设置路由键，
// 3. 交换机根据队列绑定时设置的路由键，分发给相应的队列
// ---- 这个过程就是 Direct 类型的 交换机分发消息的过程
public class DirectExchangeProducer {
	
	private static final String EXCHANGE_NAME = "hw_exchange02";
	
	public static void main(String[] args) throws IOException, TimeoutException {
	    // 获取连接
		Connection connection = RabbitmqUtil.getConnection();
		// 创建交换机(生产者绑定交换价)
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		// 发送消息到交换机中
		String msg ="exchange_msg";
		channel.basicPublish(EXCHANGE_NAME, "email", false, null, msg.getBytes());
		// channel.basicPublish(EXCHANGE_NAME, "sms", false, null, msg.getBytes());
		// 关闭通道和连接
		channel.close();
		connection.close();
	}
}
