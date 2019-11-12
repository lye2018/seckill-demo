package com.chongdu.seckill.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁类
 */
@Component
public class RedisLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     * @param key 商品skuId
     * @param clientId 用户ID
     * @param expireTime 锁失效时间
     * @return
     */
    public Boolean lock(String key, String clientId, long expireTime) {

        Boolean isLock = stringRedisTemplate.opsForValue().setIfAbsent(key, clientId, expireTime, TimeUnit.MILLISECONDS);
        logger.debug("是否取得redis分布式锁：{}", isLock);
        if (isLock) {
            String value = stringRedisTemplate.opsForValue().get(key);
            logger.debug("取得的value：{}", value);
        }
        return isLock;
    }

    /**
     * 解锁
     * @param key 商品skuId
     * @param clientId 用户Id
     */
    public void releaseLock(String key, String clientId) {

        try {

            logger.info("开始释放锁");
            String value = stringRedisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(value)
                    && clientId.equals(value)) {
                stringRedisTemplate.opsForValue().getOperations().delete(key);
                logger.info("释放锁成功");
            } else {
                logger.info("解铃还须系铃人！");
            }
        } catch (Exception e) {
            logger.error("redis分布式锁，解锁异常，{}", e.getMessage());
        }
    }

}
