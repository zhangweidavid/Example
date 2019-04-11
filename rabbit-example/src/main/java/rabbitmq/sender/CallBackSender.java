package rabbitmq.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Description
 * @Author wei.zw
 * @Date 2019-02-26 19:56
 * @Version 1.0
 **/
@Component("callbackSender")
public class CallBackSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private static final Logger logger = LoggerFactory.getLogger(CallBackSender.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String topic, String message) {
        rabbitTemplate.setConfirmCallback(this);

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        logger.info("消息id:" + correlationData.getId());
        // 用RabbitMQ发送MQTT需将exchange配置为amq.topic
        this.rabbitTemplate.convertAndSend("amq.topic", topic, message, correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("消息id:" + correlationData.getId());
        if (ack) {
            logger.info("消息发送确认成功");
        } else {
            logger.info("消息发送确认失败:" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("return--message:" + new String(message.getBody()) + ",replyCode:" + replyCode
                + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
    }

}