/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年4月14日
 */

package demo.redis.lettuce;

import java.util.concurrent.TimeUnit;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.rx.RedisReactiveCommands;
import com.lambdaworks.redis.api.sync.RedisCommands;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年4月14日 下午2:16:07
 * @version v 0.1
 */
public class SyncConnection {
	public static void main(String[] args) throws Exception {
		RedisClient redisClient = RedisClient.create("redis://localhost:6379");
		RedisCommands<String, String> connection = redisClient.connect().sync();
		System.out.println("connecting");
		connection.set("lettuce", "hello word");
		String value=connection.get("lettuce");
		System.out.println(value);
		connection.close();
		redisClient.shutdown();
	}
}
