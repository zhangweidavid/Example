package demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class RestController {

    @GetMapping("/cache/current")
    public Mono<ServerResponse> queryCurrent(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dt=simpleDateFormat.format(new Date());
        System.out.println(dt);
        return ServerResponse.ok().body(Mono.just(dt),String.class);
    }

}
