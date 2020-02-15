package main;

import com.rabbitmq.client.*;
import common.Constant;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Consumer2 {
	public static void main(String[] args) throws IOException, TimeoutException {
	    // 获取连接
		Connection connection = RabbitmqUtil.getConnection();
		// 创建通道
		final Channel channel = connection.createChannel();
		// 消费者关联队列
		channel.queueDeclare(Constant.QUEUE_NAME, false, false, false, null);
		// 设置消费者每次只能获取到一个消息
		channel.basicQos(1);
		DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
			// 监听获取消息
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				try {
					TimeUnit.SECONDS.sleep(1);
					System.out.println("消费者2获取到消息："+new String(body, "UTF-8"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					// 手动应答告诉队列服务器消息已经处理完成
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		// 获取消息，并设置自动应答模式，true 表示自动应答，false 表示手都应答
		channel.basicConsume(Constant.QUEUE_NAME, false, defaultConsumer);
	}
}
