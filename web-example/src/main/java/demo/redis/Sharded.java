package demo.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.internal.HostAndPort;
import io.lettuce.core.masterslave.MasterSlave;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Sharded< C extends StatefulRedisConnection,V> {

    private TreeMap<Long, String> nodes;
    private final Hashing algo = Hashing.MURMUR_HASH;
    private final Map<String, StatefulRedisConnection> resources = new LinkedHashMap<>();
    private RedisClient redisClient;
    private String password;
    private Set<HostAndPort> sentinels;
    private RedisCodec<String, V> codec;

    public Sharded(List<String> masters, RedisClient redisClient, String password, Set<HostAndPort> sentinels, RedisCodec<String, V> codec) {
        this.redisClient = redisClient;
        this.password = password;
        this.sentinels = sentinels;
        this.codec = codec;
        initialize(masters);
    }

    private void initialize(List<String> masters) {
        nodes = new TreeMap<>();

        for (int i = 0; i != masters.size(); ++i) {
            final String master = masters.get(i);
            for (int n = 0; n < 160; n++) {
                nodes.put(this.algo.hash("SHARD-" + i + "-NODE-" + n), master);
            }
            RedisURI.Builder builder = RedisURI.builder();
            for (HostAndPort hostAndPort : sentinels) {
                builder.withSentinel(hostAndPort.getHostText(), hostAndPort.getPort());
            }

            RedisURI redisURI = builder.withPassword(password).withSentinelMasterId(master).build();
            resources.put(master, MasterSlave.connect(redisClient, codec, redisURI));
        }

    }

    public StatefulRedisConnection getConnectionBy(String key) {
        return resources.get(getShardInfo(SafeEncoder.encode(key)));
    }

    public Collection<StatefulRedisConnection> getAllConnection(){
        return Collections.unmodifiableCollection(resources.values());
    }

    public String getShardInfo(byte[] key) {
        SortedMap<Long, String> tail = nodes.tailMap(algo.hash(key));
        if (tail.isEmpty()) {
            return nodes.get(nodes.firstKey());
        }
        return tail.get(tail.firstKey());
    }


    public void close(){
       for(StatefulRedisConnection connection:  getAllConnection()){
            connection.close();
        }
    }

    private static  class SafeEncoder {

         static byte[] encode(final String str) {
            try {
                if (str == null) {
                    throw new IllegalArgumentException("value sent to redis cannot be null");
                }
                return str.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private interface Hashing {
        Hashing MURMUR_HASH = new MurmurHash();

        long hash(String key);

        long hash(byte[] key);
    }


    private static  class MurmurHash implements Hashing {

         static long hash64A(byte[] data, int seed) {
            return hash64A(ByteBuffer.wrap(data), seed);
        }


         static long hash64A(ByteBuffer buf, int seed) {
            ByteOrder byteOrder = buf.order();
            buf.order(ByteOrder.LITTLE_ENDIAN);

            long m = 0xc6a4a7935bd1e995L;
            int r = 47;

            long h = seed ^ (buf.remaining() * m);

            long k;
            while (buf.remaining() >= 8) {
                k = buf.getLong();

                k *= m;
                k ^= k >>> r;
                k *= m;

                h ^= k;
                h *= m;
            }

            if (buf.remaining() > 0) {
                ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
                // for big-endian version, do this first:
                // finish.position(8-buf.remaining());
                finish.put(buf).rewind();
                h ^= finish.getLong();
                h *= m;
            }

            h ^= h >>> r;
            h *= m;
            h ^= h >>> r;

            buf.order(byteOrder);
            return h;
        }

        public long hash(byte[] key) {
            return hash64A(key, 0x1234ABCD);
        }

        public long hash(String key) {
            return hash(SafeEncoder.encode(key));
        }
    }




}
