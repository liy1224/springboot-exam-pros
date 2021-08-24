package com.exam.starter.util;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheUtils implements InitializingBean {
    @Autowired
    RedisTemplate redisTemplate;

    private static RedisTemplate redis = null;

    public RedisCacheUtils() {
    }

    public void afterPropertiesSet() throws Exception {
        redis = this.redisTemplate;
    }

    public static void delete(String key) {
        redis.delete(key);
    }

    public static void deleteByPrefix(String prefix) {
        Set<String> keys = redis.keys(prefix + "*");
        redis.delete(keys);
    }

    public static Boolean hasKey(String key) {
        return redis.hasKey(key);
    }

    public static void expire(String key, long timeout, TimeUnit unit) {
        redis.expire(key, timeout, unit);
    }

    public static void set(String key, Object value) {
        redis.opsForValue().set(key, value);
    }

    public static void set(String key, Object value, long timeout, TimeUnit unit) {
        redis.opsForValue().set(key, value, timeout, unit);
    }

    public static Object get(String key) {
        return redis.opsForValue().get(key);
    }

    public static Object incr(String key) {
        return redis.opsForValue().increment(key);
    }

    public static Object incr(String key, long delta) {
        return redis.opsForValue().increment(key, delta);
    }

    public static Boolean isMemberSet(String key, Object value) {
        return redis.opsForSet().isMember(key, value);
    }

    public static Object setIfAbsent(String key, Object value, long timeout, TimeUnit timeUnit) {
        redis.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
        return redis.opsForValue().get(key);
    }

    public static void put(String key, String hashKey, Object value) {
        redis.opsForHash().put(key, hashKey, value);
    }

    public static Boolean hasKey(String key, String hashKey) {
        return redis.opsForHash().hasKey(key, hashKey);
    }

    public static void delete(String key, String hashKey) {
        redis.opsForHash().delete(key, new Object[]{hashKey});
    }

    public static Object get(String key, String hashKey) {
        return redis.opsForHash().get(key, hashKey);
    }

    public static Double score(String zkey, String key) {
        return redis.opsForZSet().score(zkey, key);
    }

    public static void add(String zkey, String key, double score) {
        redis.opsForZSet().add(zkey, key, score);
    }

    public static void incrementScore(String zkey, String key, double score) {
        redis.opsForZSet().incrementScore(zkey, key, score);
    }

    public static Set getTopList(String zkey, long min, long max) {
        return redis.opsForZSet().reverseRange(zkey, min, max);
    }

    public static Set getRangeByScore(String zkey, double min, double max) {
        return redis.opsForZSet().rangeByScore(zkey, min, max);
    }

    public static void remove(String zkey, String key) {
        redis.opsForZSet().remove(zkey, new Object[]{key});
    }

    public static void push(String key, Object value) {
        redis.opsForList().remove(key, 0L, value);
        redis.opsForList().leftPush(key, value);
    }

    public static void push(String key, Object value, int maxSize) {
        redis.opsForList().remove(key, 0L, value);
        redis.opsForList().leftPush(key, value);
        long size = redis.opsForList().size(key);
        if (size > (long)maxSize) {
            redis.opsForList().trim(key, 0L, (long)(maxSize - 1));
        }

    }

    public static List getList(String key) {
        return redis.opsForList().range(key, 0L, -1L);
    }

    public static List getList(String key, long start, long end) {
        return redis.opsForList().range(key, start, end);
    }

    public static void setList(String key, Collection values, long timeout, TimeUnit unit) {
        redis.opsForList().trim(key, 0L, 0L);
        redis.opsForList().leftPop(key);
        if (CollUtil.isNotEmpty(values)) {
            redis.opsForList().leftPushAll(key, values);
        }

        redis.expire(key, timeout, unit);
    }

    public static Long setSet(String key, Object... values) {
        return redis.opsForSet().add(key, values);
    }
}
