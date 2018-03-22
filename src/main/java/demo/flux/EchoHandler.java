package demo.flux;

import demo.rabbitmq.sender.FanoutSender;
import demo.rabbitmq.sender.HelloSender2;
import demo.rabbitmq.sender.TopicSender;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.internal.operators.flowable.FlowablePublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.security.PublicKey;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class EchoHandler {
    @Autowired
    private demo.rabbitmq.sender.HelloSender1  helloSender1;

    @Autowired
    private HelloSender2 helloSender2;

    @Autowired

    private TopicSender topicSender;


    @Autowired
    private FanoutSender fanoutSender;


    public Mono<ServerResponse> echo(ServerRequest request) {

        return ServerResponse.ok().body(Mono.from(FlowablePublish.create(Flowable.range(1,100),1000)),Integer.class);
    }

    public Mono<ServerResponse> hello(ServerRequest request){
        helloSender1.send();
        return ServerResponse.ok().body(Mono.empty(),Void.class);
    }

    public Mono<ServerResponse> oneToMore(ServerRequest request){
        for(int i=0;i<10;i++){
            helloSender1.send();
        }
        return ServerResponse.ok().body(Mono.empty(),Void.class);
    }


    public Mono<ServerResponse> more2More(ServerRequest request){
        for(int i=0;i<10;i++){
            helloSender1.send();
            helloSender2.send();
        }
        return ServerResponse.ok().body(Mono.empty(),Void.class);
    }

    public Mono<ServerResponse> topicTest(ServerRequest request){
        topicSender.send();
        return ServerResponse.ok().body(Mono.empty(),Void.class);
    }

    public Mono<ServerResponse> fanoutTest(ServerRequest request){
        fanoutSender.send();
        return ServerResponse.ok().body(Mono.empty(),Void.class);
    }

}