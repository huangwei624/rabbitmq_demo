package utils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqUtil {
	
	/**
	 * 获取mq连接
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public static Connection getConnection() throws IOException, TimeoutException {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		// 设置mq服务主机地址
		connectionFactory.setHost("106.14.58.20");
		// 设置用户名
		connectionFactory.setUsername("admin_huangwei");
		// 设置密码
		connectionFactory.setPassword("123456");
		// 设置端口号
		connectionFactory.setPort(5672);
		// 设置 virtualHost
		connectionFactory.setVirtualHost("/hw_host");
		Connection connection = connectionFactory.newConnection();
		return connection;
	}
	
}
