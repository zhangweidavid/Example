/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月10日
 */

package demo.redis.lettuce;

import com.lambdaworks.redis.LettuceFutures;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.lambdaworks.redis.sentinel.api.async.RedisSentinelAsyncCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Desc:TODO config.useSentinelServers() .setPassword("abcd1234")
 * .setMasterName(master) .addSentinelAddress("10.165.126.195:26009");
 * clients.add(Redisson.create(config))
 * 
 * @author wei.zw
 * @since 2017年5月10日 上午10:19:51
 * @version v 0.1
 */
public class AsyncDemo {
	public static void main(String[] args) throws Exception {
		RedisURI redisUri = RedisURI.create("redis://10.165.126.195:26009");

		RedisSentinelAsyncCommands<String, String> connection = RedisClient.create(redisUri).connectSentinel().async();
		// connection.
		List<RedisAsyncCommands> clients = new ArrayList<>();
		for (Map<String, String> entry : connection.masters().get()) {
			clients.add(RedisClient.create(RedisURI.Builder.redis(entry.get("ip"), Integer.valueOf(entry.get("port")))
					.withPassword("abcd1234").build()).connect().async());
		}
		System.out.println(System.currentTimeMillis());
		// RedisFuture[] rf = new RedisFuture[3];
		int i = 0;
		for (RedisAsyncCommands async : clients) {

			async.get("PACKAGE_SCHEME_FOR_ONLINE_6758747").thenAccept(f -> {
				System.out.println(String.valueOf(f));
			});

		}
		//
		//
		// LettuceFutures.awaitAll(1000, TimeUnit.SECONDS, rf);
		// for (RedisFuture<String> f : rf) {
		// System.out.println(System.currentTimeMillis() + "," +
		// Thread.currentThread().getName() + " " + f.get());
		// }
		// System.out.println(System.currentTimeMillis() - start);
		// new Thread(() -> {
		// System.out.println("thread 1");
		// }).start();
	}
}
