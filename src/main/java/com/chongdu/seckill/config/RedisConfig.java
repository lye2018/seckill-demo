package com.chongdu.seckill.config;

import java.lang.reflect.Method;

import com.dyuproject.protostuff.ProtostuffIOUtil;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{

	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {

			@Override
			public Object generate(Object target, Method method, Object... params) {

				// 格式化缓存key字符串
				StringBuilder sb = new StringBuilder();
				
				// 追加类名
				sb.append(target.getClass().getName());
				
				// 追加方法名
				sb.append(method.getName());
				
				// 遍历参数并且追加
				for (Object param: params) {
					sb.append(param.toString());
				}
				return sb.toString();
			}
			
		};
	}
	
//	/**
//	 * 采用redisCacheManager作为缓存管理器
//	 * @param connectionFactory
//	 * @return
//	 */
//	@Bean
//	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//		RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
//		return redisCacheManager;
//	}
	
	@Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);
        
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        
        // 使用fastjson序列化
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);
        
        // 值采用json序列化
        template.setValueSerializer(serializer);
        
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        
        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}
