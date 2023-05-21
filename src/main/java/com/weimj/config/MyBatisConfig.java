package com.weimj.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.weimj.handler.mybatis.BigIntToStringTypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:Weimj
 * @Date: 2023/5/21  2:30
 */
@Configuration
public class MyBatisConfig {

    public static  MybatisConfiguration mybatisConfiguration;
    @Bean
    public BigIntToStringTypeHandler bigIntToStringTypeHandler() {
        return new BigIntToStringTypeHandler();
    }

    public static void  validateTypeHandlerRegistration() {
        TypeHandlerRegistry typeHandlerRegistry = mybatisConfiguration.getTypeHandlerRegistry();
        BigIntToStringTypeHandler handler = (BigIntToStringTypeHandler)typeHandlerRegistry.getTypeHandler(String.class);

        if (handler != null) {
            System.out.println("BigIntToStringTypeHandler has been successfully registered!");
        } else {
            System.out.println("Failed to register BigIntToStringTypeHandler!");
        }
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return  new ConfigurationCustomizer() {
            @Override
            public void customize(MybatisConfiguration configuration) {
                // 在这里注册自定义类型处理器
                configuration.getTypeHandlerRegistry().register(BigIntToStringTypeHandler.class);
                mybatisConfiguration= configuration;
            }
        };
    }
}
