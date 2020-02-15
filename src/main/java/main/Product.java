package main;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import common.Constant;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者（产生消息）
 */
public class Product {
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = RabbitmqUtil.getConnection();
		// 创建通道
		Channel channel = connection.createChannel();
		// 创建队列
		channel.queueDeclare(Constant.QUEUE_NAME, false, false, false, null);
		// 设置消费者每次只能获取到一个消息
		channel.basicQos(1);
		for (int i=1; i<=10; i++) {
			// 定义消息
			String msg = "hello world"+i;
			// 发送消息
			channel.basicPublish("", Constant.QUEUE_NAME, null, msg.getBytes());
		}
		// 关闭通道和连接
		channel.close();
		connection.close();
	}
}
