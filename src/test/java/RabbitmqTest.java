import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;
import utils.RabbitmqUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitmqTest {

	
	@Test
	public void test1() throws IOException, TimeoutException {
		Connection connection = RabbitmqUtil.getConnection();
		System.out.println(connection);
	}
	
	@Test
	public void test2() throws IOException, TimeoutException {
	
	}
	
}
