package com.chongdu.seckill.common.service.impl;

import com.chongdu.seckill.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key,delta);
    }

    @Override
    public boolean setExp(String key, String value, Long expireTime) {

        boolean result = false;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(stringRedisTemplate.getConnectionFactory());
        }
        return result;
    }

    @Override
    public void removes(String... keys) {

        for (String key : keys) {
            remove(key);
        }

    }

    @Override
    public void zsset(String key, String val, long score) {
        try {
            ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
            zset.add(key, val, score);
        } finally {
            RedisConnectionUtils.unbindConnection(stringRedisTemplate.getConnectionFactory());
        }
    }

    @Override
    public boolean zdel(String key, String... val) {

        try {
            ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
            zset.remove(key,val);
            return zset.remove(key,val) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisConnectionUtils.unbindConnection(stringRedisTemplate.getConnectionFactory());
        }
        return false;
    }

    @Override
    public Set<String> rangeByScore(String key, long start, long end) {
        try {
            ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
            return zset.rangeByScore(key, start, end);
        } finally {
            RedisConnectionUtils.unbindConnection(stringRedisTemplate.getConnectionFactory());
        }
    }

    @Override
    public Double getScore(String key, String member) {
        try {
            ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
            return zset.score(key,member);
        } finally {
            RedisConnectionUtils.unbindConnection(stringRedisTemplate.getConnectionFactory());
        }
    }

    @Override
    public <T> boolean set(String key, T value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, value);
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> value) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return (T) operations.get(key);
    }

    @Override
    public <T> boolean delete(String key) {
        Boolean ret = redisTemplate.delete(key);
        return (ret != null && ret );
    }


    @SuppressWarnings("unchecked")
    public <T> boolean addToSet(String key, T value) {
        BoundSetOperations<String, T> operations = (BoundSetOperations<String, T>)redisTemplate.boundSetOps(key);
        operations.add(value);
        return true;
    }

    @Override
    public <T> boolean deleteFromSet(String key, T value) {
        BoundSetOperations<String, Object> operations = redisTemplate.boundSetOps(key);
        operations.remove(value);
        return true;

    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getFromSet(String key, Class<T> valueClazz) {
        BoundSetOperations<String, T> operations = (BoundSetOperations<String, T>)redisTemplate.boundSetOps(key);
        Set<T> mems = operations.members();
        return new ArrayList<T>(mems);
    }


}
