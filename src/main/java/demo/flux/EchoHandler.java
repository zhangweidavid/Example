package demo.flux;

import demo.rabbitmq.sender.FanoutSender;
import demo.rabbitmq.sender.HelloSender2;
import demo.rabbitmq.sender.TopicSender;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.internal.operators.flowable.FlowablePublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Component
public class EchoHandler {
    @Autowired
    private demo.rabbitmq.sender.HelloSender1 helloSender1;

    @Autowired
    private HelloSender2 helloSender2;

    @Autowired

    private TopicSender topicSender;


    @Autowired
    private FanoutSender fanoutSender;


    public Mono<ServerResponse> echo(ServerRequest request) {

        return ServerResponse.ok().body(Mono.from(FlowablePublish.create(Flowable.range(1, 100), 1000)), Integer.class);
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        helloSender1.send();
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
        ClientResources resources=DefaultClientResources.builder().ioThreadPoolSize(100).build();
        RedisClient redisClient = RedisClient.create(resources);
        RedisURI test6009 = RedisURI.builder().withSentinel("10.165.126.195", 26009).withSentinel("10.165.126.195", 26009)
                .withPassword("abcd1234").withSentinelMasterId("test6009").build();

        RedisURI test6008 = RedisURI.builder().withSentinel("10.165.126.195", 26009).withSentinel("10.165.126.195", 26009)
                .withPassword("abcd1234").withSentinelMasterId("test6008").build();

        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient, new Utf8StringCodec(),
                test6009);

        StatefulRedisMasterSlaveConnection<String, String> connectiontest6008 = MasterSlave.connect(redisClient, new Utf8StringCodec(),
                test6008);
        connection.setReadFrom(ReadFrom.SLAVE);

        System.out.println("Connected to Redis");
        RedisAsyncCommands<String, String> redisCommands = connection.async();
        redisCommands.set("test","value");
        for(int i=0;i<10000;i++) {
            try {
                System.out.println(redisCommands.get("test").get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        connection.close();
        redisClient.shutdown();
    }

}