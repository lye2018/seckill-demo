package com.chongdu.seckill.common.service;

import java.util.List;
import java.util.Set;

public interface RedisService {

    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 设置超期时间
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

    /**
     * 写入缓存设置失效时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setExp(final String key, String value, Long expireTime);

    /**
     * 批量删除对应的value
     * @param keys
     */
    public void removes(final String...keys);

    /**
     * 有序集合添加
     * @param key
     * @param val
     * @param score
     */
    void zsset(String key, String val, long score);

    /**
     * 删除zset元素
     * @param key
     * @param val
     * @return
     */
    boolean zdel(String key, String... val);

    /**
     * 获取范围内的元素，低分--到高分排序
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<String> rangeByScore(String key, long start, long end);

    Double getScore(String key, String member);

    // redisTemplate 方法 如下
    /**
     * redisTemplate set
     * @param key
     * @param value
     * @return
     */
    public <T> boolean set(String key, T value);

    /**
     * redisTemplate get
     * @param key
     * @param value
     * @return
     */
    public <T> T get(String key, Class<T> value);

    /**
     * redisTemplate delete
     * @param key
     * @return
     */
    public <T> boolean delete(String key);

    /**
     * redisTemplate addToSet
     * @param key
     * @param value
     * @return
     */
    public <T> boolean addToSet(String key, T value);

    /**
     * redisTemplate deleteFromSet
     * @param key
     * @param value
     * @return
     */
    public <T>boolean deleteFromSet(String key, T value);

    /**
     * redisTemplate getFromSet
     * @param key
     * @param valueClazz
     * @return
     */
    public <T>List<T> getFromSet(String key, Class<T> valueClazz );
}
