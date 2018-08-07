package demo.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.event.EventBus;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.lettuce.core.models.role.RedisNodeDescription;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Configuration
public class LettuceConfig {

    /**
     * 配置客户端资源
     *
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.builder().ioThreadPoolSize(8).computationThreadPoolSize(10).build();
    }


    /**
     * 配置Socket选项
     * keepAlive=true
     * tcpNoDelay=true
     * connectionTimeout=5秒
     *
     * @return
     */
    @Bean
    SocketOptions socketOptions() {
        return SocketOptions.builder().keepAlive(true).tcpNoDelay(true).connectTimeout(Duration.ofSeconds(5)).build();
    }

    /**
     * 配置客户端选项
     *
     * @return
     */
    @Bean
    ClientOptions clientOptions(SocketOptions socketOptions) {
        return ClientOptions.builder().socketOptions(socketOptions).build();
    }

    /**
     * 创建RedisClient
     *
     * @param clientResources 客户端资源
     * @param clientOptions   客户端选项
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    RedisClient redisClient(ClientResources clientResources, ClientOptions clientOptions, RedisURI redisURI) {
        RedisClient client = RedisClient.create(clientResources, redisURI);
        client.setOptions(clientOptions);
        client.getResources().eventBus().get().subscribe(e -> {
            System.out.println("client 订阅事件： " + e);
        });
        return client;
    }


    @Bean
    RedisURI redisURI() {
        return RedisURI.builder().withSentinel("10.165.126.195", 26009).withPassword("abcd1234").withSentinelMasterId("test6009").build();
    }



    @Bean
    EventBus eventBus(RedisClient redisClient) {
        return redisClient.getResources().eventBus();
    }

    /**
     * 创建连接
     *
     * @param redisClient
     * @return
     */
//    @Bean(destroyMethod = "close")
//    StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
//        return redisClient.connect();
//    }


//    /**
//     * 哨兵
//     */
//    @Bean(destroyMethod = "close")
//    StatefulRedisMasterSlaveConnection<String, String> statefulRedisMasterSlaveConnection(RedisClient redisClient, RedisURI redisURI) {
//        StatefulRedisMasterSlaveConnection connection = MasterSlave.connect(redisClient, new Utf8StringCodec(), redisURI);
//        connection.setReadFrom(new ReadFrom() {
//            @Override
//            public List<RedisNodeDescription> select(Nodes nodes) {
//                List<RedisNodeDescription> list = nodes.getNodes();
//                Collections.shuffle(list);
//                return list;
//            }
//        });
//        return connection;
//    }

//    /**
//     * 单节点直连模式
//     */
//    @Bean(destroyMethod = "close" ,name = "singletoneConnection")
//    StatefulRedisConnection<String,String> singletoneConnection(ClientResources clientResources){
//        return RedisClient.create(clientResources).connect(RedisURI.builder().withHost("127.0.0.1").withPort(6379).build());
//    }


    @Bean(name="clusterRedisURI")
    RedisURI clusterRedisURI(){
        return RedisURI.builder().withHost("10.160.77.32").withPort(6954).build();
    }

    @Bean
    ClusterClientOptions clusterClientOptions(){
        return ClusterClientOptions.builder().autoReconnect(true).maxRedirects(1).build();
    }

    @Bean
    RedisClusterClient redisClusterClient(ClientResources clientResources, ClusterClientOptions clusterClientOptions, RedisURI clusterRedisURI){
        RedisClusterClient redisClusterClient= RedisClusterClient.create(clientResources,clusterRedisURI);
        redisClusterClient.setOptions(clusterClientOptions);
        return redisClusterClient;
    }
    /**
     * 集群模式
     */
    @Bean(destroyMethod = "close")
    StatefulRedisClusterConnection<String,String> statefulRedisClusterConnection(RedisClusterClient redisClusterClient){
        return  redisClusterClient.connect();
    }

}
