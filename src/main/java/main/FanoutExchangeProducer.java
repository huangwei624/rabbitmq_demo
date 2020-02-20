package main;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//  模拟生产者向 fanout交换机中发送消息
public class FanoutExchangeProducer {
	
	private static final String EXCHANGE_NAME = "hw_exchange01";
	
	public static void main(String[] args) throws IOException, TimeoutException {
	    // 获取连接
		Connection connection = RabbitmqUtil.getConnection();
		// 创建交换机(生产者绑定交换价)
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		// 发送消息到交换机中
		String msg ="exchange_msg";
		channel.basicPublish(EXCHANGE_NAME, "", false, null, msg.getBytes());
		// 关闭通道和连接
		channel.close();
		connection.close();
	}
}
