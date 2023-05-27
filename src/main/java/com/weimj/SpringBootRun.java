package com.weimj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 下一步要做的事
 * 支持hash_line
 * 简化任务配置
 * 多数据源
 *  引入cancl 支持增量实时同步
 *  支持以jar包的形式引入，并直接配置启动
 */
@SpringBootApplication
public class SpringBootRun {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRun.class,args);
    }


}
