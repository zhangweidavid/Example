package com.example.corejava;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;



/**
 * Created by wei.zw on 2017/5/18.
 */
public class LocalCacheService {


    /** 默认的缓存容量***/
    private static int DEFAULT_CAPACITY = 2048;

    /**最大容量 Map容量扩展阀值为0.99 限制为20000可以最大限度利用Map空间而且不会出在此扩展Map大小**/
    private static int MAX_CAPACITY = 20000;

    /*** 刷新缓存的频率***/
    private static int MONITOR_DURATION = 2;


    /** 写入数据锁 */
    private static final ReentrantLock putLock = new ReentrantLock();

    /**调度线程池**/
    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    /***启动监控线程***/
    static {
        service.scheduleAtFixedRate(new TimeoutTimerMonitor(), MONITOR_DURATION, MONITOR_DURATION, TimeUnit.SECONDS);
    }

    /***使用默认容量创建一个Map***/
    private static Map<String, Reference<CacheEntity>> cache = new ConcurrentHashMap<>(DEFAULT_CAPACITY,
            0.99f);

    private static Queue<String> keyList = new ConcurrentLinkedQueue<>();

    private LocalCacheService() {

    }

    public static LocalCacheService getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private static LocalCacheService instance = new LocalCacheService();
    }

    public  int getLocalCacheSize(){
        return cache.size();
    }


    /**
     * 将key-value 保存到本地缓存并制定该缓存的过期时间
     *
     * @param key
     * @param value
     * @param expireTime
     *            过期时间 秒，如果是-1 则表示永不过期
     * @return
     */
    public boolean putValue(String key, Object value, int expireTime) {
        // 如果缓存达到上线时清除最久未使用数据
        // 锁
        boolean lock = putLock.tryLock();
        //获取锁失败，直接返回
        if (!lock) {
            return false;
        }
        //获取锁存入缓存
        try {
            //如果缓存key队列大小已经达到最大值则需要清楚最久未使用缓存数据
            if (keyList.size() >= MAX_CAPACITY) {
                String oldestKey = keyList.poll();
                Object o=cache.remove(oldestKey);
                o=null;
            }
            //将当前缓存key存入缓存key队列
            keyList.add(key);
            //缓存数据
            return putCloneValue(key, value, expireTime);

        } finally {
            if (lock) {
                putLock.unlock();
            }
        }

    }

    /**
     * 将值通过序列化clone 处理后保存到缓存中，可以解决值引用的问题
     *
     * @param key
     * @param value
     * @param expireTime
     *            秒
     * @return
     */
    private boolean putCloneValue(String key, Object value, int expireTime) {
        try {

            if (cache.size() >= MAX_CAPACITY) {
                return false;
            }
            // 序列化赋值
            CacheEntity entityClone = new CacheEntity(value, System.nanoTime(), expireTime);
            cache.put(key, TimeoutTimerMonitor.fold(entityClone));
            return true;
        } catch (Exception e) {
            //logger.warn("存入指定key=" + key + ", value=" + ToStringBuilder.reflectionToString(value) + ",异常", e);
        }
        return false;
    }

    /**
     * 从本地缓存中获取key对应的值，如果该值不存则则返回null
     *
     * @param key
     * @return
     */
    public Object getValue(String key) {
            // 清除key
            keyList.remove(key);
            //获取缓存对象
            CacheEntity entry = TimeoutTimerMonitor.unfold(cache.get(key));

            // 判断缓存是否过期
            if (TimeoutTimerMonitor.isExpire(entry)) {
                //清空缓存
                TimeoutTimerMonitor.clearByKey(key);
                return null;
            }
            Object result = entry.getValue();
            if (result != null) {
                // 添加到对尾
                keyList.add(key);
            }
            return result;

    }





    /**
     *
     * Desc:TODO
     *
     * @author wei.zw
     * @since 2016年8月26日 下午6:51:17
     * @version v 0.1
     */
    static class TimeoutTimerMonitor implements Runnable {

        public void run() {
            try {
                checkTime();
            } catch (Exception e) {
                //logger.warn("缓存过期检查发生异常", e);
            }

        }

        /**
         * 过期缓存的具体处理方法
         *
         * @throws Exception
         */
        private void checkTime() throws Exception {
            // "开始处理过期 ";

            for (String key : keyList) {
                CacheEntity tce = unfold(cache.get(key));
                if (isExpire(tce)) {
                    clearByKey(key);
                }

            }
        }

        public static boolean isExpire(CacheEntity tce) {
            if (tce == null) {
                return true;
            }
            if (tce.getExpire() < 0) {
                return false;
            }
            // 计算超时时间
            long timoutTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - tce.getGmtModify());

            return tce.getExpire() < timoutTime;

        }

        public static void clearByKey(String key) {

                cache.remove(key);
                keyList.remove(key);

        }

        public static Reference<CacheEntity> fold(CacheEntity value) {
            return new WeakReference<CacheEntity>(value);
        }

        public static CacheEntity unfold(Reference<CacheEntity> ref) {
            if (ref == null) {
                return null;
            }

            return ref.get();
        }
    }
}
