/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年4月14日
 */

package demo.redis.lettuce;

import java.util.concurrent.TimeUnit;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.rx.RedisReactiveCommands;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年4月14日 上午11:54:05
 * @version v 0.1
 */
public class RxConnection {
	public static void main(String[] args) throws Exception {
		RedisClient redisClient = RedisClient.create("redis://localhost:6379");
		RedisReactiveCommands<String, String> reactiveConnection = redisClient.connect().reactive();
		System.out.println("connecting");
		reactiveConnection.del("lettuce").subscribe(System.out::println);
		reactiveConnection.exists("lettuce").subscribe(System.out::println);
		reactiveConnection.set("lettuce", "hello word").subscribe(System.out::println);
		reactiveConnection.get("lettuce").subscribe(System.out::println);
		reactiveConnection.dump("lettuce").subscribe(System.out::println);
		reactiveConnection.dump("not_exist_key").subscribe(System.out::println);
		TimeUnit.SECONDS.sleep(10);
		reactiveConnection.close();
		redisClient.shutdown();
	}
}
