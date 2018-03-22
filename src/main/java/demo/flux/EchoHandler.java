package demo.flux;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.internal.operators.flowable.FlowablePublish;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class EchoHandler {

    public Mono<ServerResponse> echo(ServerRequest request) {

        return ServerResponse.ok().body(Mono.from(FlowablePublish.create(Flowable.range(1,100),1000)),Integer.class);
    }

}