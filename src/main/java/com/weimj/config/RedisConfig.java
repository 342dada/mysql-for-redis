package com.weimj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @Author:Weimj
 * @Date: 2023/5/21  21:00
 */
@Configuration
public class RedisConfig {
//        @Bean
//        JedisConnectionFactory jedisConnectionFactory() {
//            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("localhost", 6379);
//            return new JedisConnectionFactory(configuration);
//        }
//
//        @Bean
//        RedisTemplate<String, Object> redisTemplate() {
//            RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//            redisTemplate.setConnectionFactory(jedisConnectionFactory());
//            return redisTemplate;
//        }
        @Bean

        JedisPool JedisPoolFactory(){
            JedisPool localhost = new JedisPool("localhost", 6379);
            return  localhost;
        }

}
