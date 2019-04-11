package demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;

/**
 * @Description
 * @Author wei.zw
 * @Date 2019-02-26 19:52
 * @Version 1.0
 **/
@Configurable
@EnableRabbit
@ComponentScan(basePackages = {"com","rabbitmq","demo"})
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Bean
    public Queue userQueue() {
        return new Queue("user");
    }

    //===============以下是验证topic Exchange的队列==========
    @Bean
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }
    //===============以上是验证topic Exchange的队列==========


    //===============以下是验证Fanout Exchange的队列==========
    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }
    //===============以上是验证Fanout Exchange的队列==========


    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     *
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     *
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    @Bean
    Binding bindingExchangeA(Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }


    final static String EXCHANGE_NAME = "amq.topic";
    final static String QUEUE_NAME = "topic.baqgl.*.*";
    final static String ROUTING_KEY = "topic.baqgl.#";

    @Bean
    public ConnectionFactory connectionFactory(@Value("${spring.rabbitmq.host}") String addresses,
                                               @Value("${spring.rabbitmq.port}") String port,
                                               @Value("${spring.rabbitmq.username}") String username,
                                               @Value("${spring.rabbitmq.password}") String password,
                                               @Value("${spring.rabbitmq.publisher-confirms}") boolean publisherConfirms) {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses + ":" + port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        /** 如果要进行消息回调，则这里必须要设置为true */
        connectionFactory.setPublisherConfirms(publisherConfirms);
        return connectionFactory;
    }

    @Bean
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        return template;
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("hello");
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(helloQueue()).to(directExchange()).withQueueName();
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange("amq.headers");
    }

    @Bean
    public Binding headersBinding() {
        return BindingBuilder.bind(queue()).to(headersExchange()).whereAny("test").exist();
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY);
    }


    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue(), helloQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(new ChannelAwareMessageListener() {

            @Override
            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                try {
                    logger.info(
                            "消费端接收到消息:" + message.getMessageProperties() + ":" + new String(message.getBody()));
                    logger.info("topic:" + message.getMessageProperties().getReceivedRoutingKey());
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), true); // false只确认当前一个消息收到，true确认所有consumer获得的消息
                } catch (Exception e) {
                    e.printStackTrace();

                    if (message.getMessageProperties().getRedelivered()) {
                        logger.info("消息已重复处理失败,拒绝再次接收...");
                        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // 拒绝消息
                    } else {
                        logger.info("消息即将再次返回队列处理...");
                        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // requeue为是否重新回到队列
                    }
                }
            }
        });
        return container;
    }

}
