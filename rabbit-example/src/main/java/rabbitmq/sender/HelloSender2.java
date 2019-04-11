package rabbitmq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloSender2 {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String sendMsg = "hello2 " + new Date();
        System.out.println("----------  Sender2 : " + sendMsg);
        this.rabbitTemplate.convertAndSend("hello", sendMsg);
    }
}
