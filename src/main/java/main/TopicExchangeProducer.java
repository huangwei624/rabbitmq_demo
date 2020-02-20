package main;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// 模拟生产者向 topic 类型的交换机 发送消息
// topic 与 direct 类型的不同主要是topic类型支持了通配符 的 routing key （#， *） 这两种通配符
// # ：可以匹配多个单词； * ：只能匹配一个单词，例如：
//        ---------->> log.email.sms 和 *.email.sms 匹配
//        ---------->> log.email.sms 和 #.sms 匹配
public class TopicExchangeProducer {
	
	private static final String EXCHANGE_NAME = "hw_exchange03";
	
	public static void main(String[] args) throws IOException, TimeoutException {
	    // 获取连接
		Connection connection = RabbitmqUtil.getConnection();
		// 创建交换机(生产者绑定交换价)
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		// 发送消息到交换机中
		String msg ="exchange_msg";
		channel.basicPublish(EXCHANGE_NAME, "log.email", false, null, msg.getBytes());
		channel.basicPublish(EXCHANGE_NAME, "log.sms", false, null, msg.getBytes());
		// 关闭通道和连接
		channel.close();
		connection.close();
	}
}
