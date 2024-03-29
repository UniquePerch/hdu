package com.hdu.hdufpga.util;

import cn.hutool.core.util.RandomUtil;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class LocalCache<K, V> {

    /**
     * 扫描时间，单位：毫秒
     */
    private final static Long SCAN_TIME_INTERVAL = 1000L;
    /**
     * 扫描线程名称
     */
    private final static String SCAN_THREAD_NAME = "cache-scan-thread";
    /**
     * 一次扫描选择多少个元素
     */
    private final static Integer SCAN_ONCE_NUMBER = 5;
    private final ConcurrentHashMap<K, CacheObject<V>> cache = new ConcurrentHashMap<>();

    public LocalCache() {
        initRegularRandomElimination();
    }

    /**
     * 存储key-value
     *
     * @param key      关键词
     * @param value    值
     * @param interval 时间间隙
     * @param unit     时间单位
     */
    public void put(K key, V value, long interval, TimeUnit unit) {
        if (key == null) {
            throw new NullPointerException("cache key is null");
        }
        if (value == null) {
            throw new NullPointerException("cache value is null");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("cache internal time is negative number");
        }
        long expireTime = System.currentTimeMillis() + unit.toMillis(interval);
        cache.put(key, new CacheObject<>(value, expireTime));
    }

    /**
     * 根据key获取value
     * <p>如果已经过期则会被移除</p>
     *
     * @param key     关键词
     * @param deleted 获取之后是否删除
     * @return 值
     */
    public V get(K key, boolean deleted) {
        if (key == null) {
            throw new NullPointerException("cache key is null");
        }
        V value = getAndRemoveIfExpired(key);
        if (deleted && value != null) {
            cache.remove(key);
        }
        return value;
    }

    /**
     * 根据value获取key但是不删除
     *
     * @param key 关键词
     * @return value
     * @see LocalCache#get(Object, boolean)
     */
    public V get(K key) {
        return get(key, false);
    }

    private void initRegularRandomElimination() {
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(SCAN_TIME_INTERVAL);

                } catch (Exception e) {

                }
            }
        }, SCAN_THREAD_NAME);
    }

    private void regularRandomElimination() {
        Object[] keyArr = cache.keySet().toArray();
        if (keyArr.length == 0) {
            return;
        }
        int scanCount = Math.min(keyArr.length, SCAN_ONCE_NUMBER);
        Set<K> keySet = new HashSet<>(scanCount);
        for (int i = 0; i < scanCount; i++) {
            int index = RandomUtil.randomInt(scanCount);
            keySet.add((K) keyArr[index]);
        }
        keySet.forEach(this::getAndRemoveIfExpired);
    }

    /**
     * 获取value并移除key如果已过期
     *
     * @param key 关键词
     * @return key对应value
     */
    private V getAndRemoveIfExpired(K key) {
        if (key == null) {
            throw new NullPointerException("cache key is null");
        }
        CacheObject<V> cacheObject = cache.get(key);
        if (cacheObject == null) {
            return null;
        }
        if (cacheObject.isExpired()) {
            remove(key);
            return null;
        }
        return cacheObject.getValue();
    }

    /**
     * 移除指定key的value
     *
     * @param key 关键词
     * @return 被移除的value
     */
    public V remove(K key) {
        if (key == null) {
            throw new NullPointerException("cache key is null");
        }
        CacheObject<V> cacheObject = cache.get(key);
        if (cacheObject == null) {
            return null;
        }
        cache.remove(key);
        return cacheObject.getValue();
    }


    public static class CacheObject<V> {
        @Getter
        private final V value;

        private final long expireTime;

        public CacheObject(V value, long expireTime) {
            this.value = value;
            this.expireTime = expireTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }
}
