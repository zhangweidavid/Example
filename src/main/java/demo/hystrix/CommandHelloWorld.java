package demo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import rx.Observable;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CommandHelloWorld extends HystrixCommand<String> {

    final String name;

    public CommandHelloWorld(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CircuitBreakerTestGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CircuitBreakerTestKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CircuitBreakerTest"))
                .andThreadPoolPropertiesDefaults(    // 配置线程池
                        HystrixThreadPoolProperties.Setter()
                                .withCoreSize(200)    // 配置线程池里的线程数，设置足够多线程，以防未熔断却打满threadpool
                )
                .andCommandPropertiesDefaults(    // 配置熔断器
                        HystrixCommandProperties.Setter()
                                .withCircuitBreakerEnabled(true)
                                .withCircuitBreakerRequestVolumeThreshold(3)
                                .withCircuitBreakerErrorThresholdPercentage(80)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("running run():" + name);
        int num = Integer.valueOf(name);
        if (num % 2 == 0 && num < 10) {    // 直接返回
            return name;
        } else {    // 无限循环模拟超时
            int j = 0;
            while (true) {
                j++;
            }
        }
    }

    @Override
    protected String getFallback() {
        StringBuilder sb = new StringBuilder("running fallback");
        boolean isRejected = isResponseRejected();
        boolean isException = isFailedExecution();
        boolean isTimeout = isResponseTimedOut();
        boolean isCircut = isCircuitBreakerOpen();

        sb.append(", isRejected:").append(isRejected);
        sb.append(", isException:" + isException);
        if (isException) {
            sb.append(" msg=").append(getExecutionException().getMessage());
        }
        sb.append(",  isTimeout: " + isTimeout);
        sb.append(",  isCircut:" + isCircut);

        sb.append(", group:").append(this.getCommandGroup().name());
        sb.append(", threadpool:").append(getThreadPoolKey().name());
        System.out.println(sb.toString());

        String msg = "Hello Failure " + name + "!";
        return msg;
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 50; i++) {
            String s = new CommandHelloWorld(String.valueOf(i)).execute();
            System.out.println(s);
            Future<String> future = new CommandHelloWorld(String.valueOf(i)).queue();
            Observable<String> observable = new CommandHelloWorld(String.valueOf(i)).observe();
            observable.subscribe(System.out::println);
            Observable<String> t = new CommandHelloWorld(String.valueOf(i)).toObservable();
        }
    }
}
