package com.wl.security_demo.utils;

import jakarta.annotation.Resource;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 工具类封装
 */
@Component
public class RedisCacheUtils {

    @Resource
    private RedissonClient redissonClient;

    private static final long DEFAULT_EXPIRE = 60 * 60 * 3; // 默认过期时间：24小时

    /**
     * 缓存基本的对象，Integer、String、实体类等
     */
    public <T> void setCacheObject(final String key, final T value) {
        // Redisson 中，通用对象桶叫 RBucket
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    /**
     * 缓存基本的对象，并设置过期时间
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value, timeout, timeUnit);
    }

    /**
     * 缓存基本的对象，并设置过期时间
     */
    public <T> void setCacheObjectDefaultExpire(final String key, final T value) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 获得缓存的基本对象
     */
    public <T> T getCacheObject(final String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    /**
     * 删除单个对象
     */
    public boolean deleteObject(final String key) {
        return redissonClient.getBucket(key).delete();
    }

    /**
     * 删除集合对象
     * 注意：Redisson 删除多个 key 通常使用 Keys 接口
     */
    public long deleteObject(final Collection<String> keys) {
        return redissonClient.getKeys().delete(keys.toArray(new String[0]));
    }

    /**
     * 缓存 List 数据
     */
    public <T> void setCacheList(final String key, final List<T> dataList) {
        RList<T> rList = redissonClient.getList(key);
        // 先过期旧数据（可选，看业务需求，或者直接 expire）
        rList.expire(0, TimeUnit.SECONDS);
        rList.addAll(dataList);
    }

    /**
     * 获得缓存的 list 对象
     */
    public <T> List<T> getCacheList(final String key) {
        RList<T> rList = redissonClient.getList(key);
        return rList.readAll(); // 读取所有元素
    }

    /**
     * 设置过期时间
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redissonClient.getBucket(key).expire(timeout, unit);
    }

    /**
     * 获取 Keys (注意性能)
     */
    public Collection<String> keys(final String pattern) {
        // Redisson 获取 Keys 也是通过迭代器，比 RedisTemplate keys(*) 安全一点
        return (Collection<String>) redissonClient.getKeys().getKeysByPattern(pattern);
    }
}
