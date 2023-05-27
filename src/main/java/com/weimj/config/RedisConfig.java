package com.weimj.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Author:Weimj
 * @Date: 2023/5/21  21:00
 */
@Configuration
public class RedisConfig {

@Bean
public LettuceConnectionFactory redisConnectionFactory() {

    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
    redisConfig.setHostName("localhost");
    redisConfig.setPort(6379);
//    redisConfig.setPassword(RedisPassword.of("123456"));

    LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
            .poolConfig(getLettucePoolConfig())
            .commandTimeout(Duration.ofSeconds(20))
//            .(getLettucePoolConfig())
//            .useSsl()
            .build();

    return new LettuceConnectionFactory(redisConfig, clientConfig);
}
    @Bean(name="localRedisTemplate")
    public RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }
    private GenericObjectPoolConfig<?> getLettucePoolConfig() {
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(Integer.parseInt("16"));
        genericObjectPoolConfig.setMaxIdle(Integer.parseInt("4"));
        genericObjectPoolConfig.setMinIdle(Integer.parseInt("2"));
        return genericObjectPoolConfig;
    }

}
