package demo; /**
 * @Description
 * @Author wei.zw
 * @Date 2019-02-26 20:12
 * @Version 1.0
 **/

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import rabbitmq.sender.CallBackSender;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitSenderTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CallBackSender callBackSender;

    @Test
    public void testDirect() {

        rabbitTemplate.convertAndSend("hello", "test");

    }

    @Test
    public void testHeaders() {
        Map<String, Object> map_any = new HashMap<>();
        map_any.put("test", "A");

        /**
         * 声明消息 (消息体, 消息属性)
         */
        MessageProperties messageProperties = new MessageProperties();
        // 设置消息是否持久化。Persistent表示持久化，Non-persistent表示不持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
        messageProperties.setContentType("UTF-8");
        messageProperties.getHeaders().putAll(map_any);

        Message message = new Message("hello,rabbit_headers_any！".getBytes(), messageProperties);

        rabbitTemplate.send("amq.headers", null, message);
    }


//    @Test
//    public void topic() throws Exception {
//        callBackSender.send("topic.baqgl.admin.1", "测试消息");
//    }测试消息
}

