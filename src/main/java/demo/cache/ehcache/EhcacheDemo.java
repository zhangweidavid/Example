package demo.cache.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import java.util.concurrent.TimeUnit;

public class EhcacheDemo {

    public static void main(String[] args) throws InterruptedException {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("myCache ", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build()).build(true);
        Cache<Long, String> cache = cacheManager.getCache("myCache", Long.class, String.class);

        Cache<String, String> one = cacheManager.createCache("one", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, MemoryUnit.MB)));

        Cache<String, String> two = cacheManager.createCache("two", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(100, MemoryUnit.MB)).withDispatcherConcurrency(4).withExpiry(Expirations.timeToLiveExpiration(Duration.of(10, TimeUnit.SECONDS))));

        two.put("1", "ONE");
        TimeUnit.SECONDS.sleep(5);
        System.out.println(two.get("1"));

        TimeUnit.SECONDS.sleep(5);
        System.out.println(two.get("1"));

    }


}
