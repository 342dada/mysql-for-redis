package com.weimj.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.internal.schedule.SchedulerFacade;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.weimj.entity.ProcessParam;
import com.weimj.job.TestJob;
import com.weimj.job.TestJob1;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:Weimj
 * @Date: 2023/5/22  22:52
 */
@Configuration
public class MyElasticJobConfig implements EnvironmentAware {

    private Environment environment;
    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    @Bean(initMethod = "init")
    public SchedulerInitializer myElasticJobSchedulers() {
        String property = environment.getProperty("elastic.job.param");
        Gson gson = new Gson();
        ProcessParam[] users = gson.fromJson(property, ProcessParam[].class);
        List<ProcessParam> paramList = Arrays.asList(users);
        List<JobScheduler> jobSchedulers = new ArrayList<>();
        for (ProcessParam processParam : paramList) {
            // 构造 JobCoreConfiguration、SimpleJobConfiguration、LiteJobConfiguration
            JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(processParam.getJobName(), processParam.getCron(), 1)
                    .jobParameter(JSON.toJSONString(processParam))
                    .build();
            SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, TestJob.class.getCanonicalName());
            LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration)
                    .overwrite(true)
                    .monitorExecution(true)
                    .build();
            // 构造 JobScheduler
            JobScheduler jobScheduler = new JobScheduler(registryCenter, liteJobConfiguration);
            jobSchedulers.add(jobScheduler);
        }
        return new SchedulerInitializer(jobSchedulers);
    }

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter() {
        ZookeeperConfiguration config = new ZookeeperConfiguration("localhost:2181", "elastic-job-lite-spring-boot-demo");
        return new ZookeeperRegistryCenter(config);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
