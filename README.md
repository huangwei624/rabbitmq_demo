### 一、点对点消息队列
#### 1、消息应答模式
- 默认情况下，消息应答模式是开启的，即自动应答，当消费者消费一个消息时会向队列服务
器自动应答，随即这个消息会从队列服务器中删除，即使消费者没有将消息处理完。
可以通过方法：`String basicConsume(String queue, boolean autoAck, Consumer callback)`
，autoAck设置应答模式，true代表自动应答，false代表手动应答。
```
// 获取消息，并设置自动应答模式，true 表示自动应答，false 表示手都应答
channel.basicConsume(Constant.QUEUE_NAME, true, defaultConsumer);
```
- 为了确保消息不会丢失，可以采用手动应答模式，消费者发送一个消息应答，
告诉RabbitMQ这个消息已经接收并且处理完毕了。RabbitMQ这才会将这个消息删除。
如果一个消费者挂掉却没有发送应答，RabbitMQ会理解为这个消息没有处理完全，然后交给
另一个消费者去重新处理。这样，你就可以确认即使消费者偶尔挂掉也不会丢失任何消息了。
- 没有任何消息超时限制；只有当消费者挂掉时，RabbitMQ才会重新投递。即使处理一条消息会
花费很长的时间。

#### 2、公平队列模式
- 默认情况下消息分发是平均分发给每个消费者的，这个就会面临一个问题，有的消费者的业务
比较简单，处理消息快，而有的比较慢，这就造成了有的消费者很闲有的很忙碌。为了解决这样
的问题，可以首先设置手动应答模式，让后使用`basicQos`方法告诉RabbitMQ不要在同一时间
给一个消费者超过一条消息:
```
// 设置消费者每次只能获取到一个消息，这里要注意：需要在生产者和消费者方都要设置
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
```
### 二、订阅发布消息队列