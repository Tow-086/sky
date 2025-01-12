package com.sky.config;


import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建RedisTemplate对象
        log.info("开始创建RedisTemplate对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        log.info("创建RedisTemplate对象成功！");

        // 创建String类型的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(redisTemplate.getStringSerializer());
//        redisTemplate.setHashKeySerializer(redisTemplate.getStringSerializer());
//        redisTemplate.setHashValueSerializer(redisTemplate.getStringSerializer());

        log.info("设置RedisTemplate的序列化器成功！");

        return redisTemplate;
    }
}
