package demo.flux;

import demo.rabbitmq.sender.FanoutSender;
import demo.rabbitmq.sender.HelloSender2;
import demo.rabbitmq.sender.TopicSender;
import demo.redis.Sharded;
import io.lettuce.core.KeyValue;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.event.Event;
import io.lettuce.core.event.EventBus;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.flowable.FlowablePublish;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class EchoHandler {

//    @Autowired
//    private Sharded<StatefulRedisMasterSlaveConnection, String> sharded;

    @Autowired
    private demo.rabbitmq.sender.HelloSender1 helloSender1;

    @Autowired
    private HelloSender2 helloSender2;

    @Autowired

    private TopicSender topicSender;


    @Autowired
    private FanoutSender fanoutSender;

//    @Autowired
//    private StatefulRedisMasterSlaveConnection<String, String> statefulRedisMasterSlaveConnection;

    @Autowired
    private StatefulRedisClusterConnection<String,String> statefulRedisClusterConnection;

//    @Autowired
//    private StatefulRedisConnection singletoneConnection;

    @Autowired
    private EventBus eventBus;


    public Mono<ServerResponse> echo(ServerRequest request) {

        return ServerResponse.ok().body(Mono.from(FlowablePublish.create(Flowable.range(1, 100), 1000)), Integer.class);
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        eventBus.publish(new Event() {
            @Override
            public String toString() {
                return "自定义事件";
            }
        });
//        statefulRedisMasterSlaveConnection.sync().set("test","自定义readFrom");
//        statefulRedisMasterSlaveConnection.sync().get("test");
       // singletoneConnection.sync().set("test","singletone");
        statefulRedisClusterConnection.sync().set("test","cluster");
        statefulRedisClusterConnection.sync().get("test1");

        return ServerResponse.ok().body(Mono.empty(), Void.class);
    }

    public Mono<ServerResponse> oneToMore(ServerRequest request) {
        for (int i = 0; i < 10; i++) {
            helloSender1.send();
        }
        return ServerResponse.ok().body(Mono.empty(), Void.class);
    }


    public Mono<ServerResponse> more2More(ServerRequest request) {
        for (int i = 0; i < 10; i++) {
            helloSender1.send();
            helloSender2.send();
        }
        return ServerResponse.ok().body(Mono.empty(), Void.class);
    }

    public Mono<ServerResponse> topicTest(ServerRequest request) {
        topicSender.send();
        lettuceGet();
        return ServerResponse.ok().body(Mono.empty(), Void.class);
    }

    public Mono<ServerResponse> fanoutTest(ServerRequest request) {
        fanoutSender.send();
        return ServerResponse.ok().body(Mono.empty(), Void.class);
    }

    private void lettuceGet() {
        try {
//            //只从slave节点中读取
//            StatefulRedisMasterSlaveConnection redisConnection = (StatefulRedisMasterSlaveConnection) sharded.getConnectionBy("goods_id_list_for_deposit_new");
//            //使用异步模式获取缓存值
//            System.out.println(redisConnection.async().get("key").get());

        } catch (Exception e) {

        }
    }

}