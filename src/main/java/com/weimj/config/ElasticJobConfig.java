package com.weimj.config;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.weimj.job.TestJob;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticJobConfig implements ApplicationContextAware {
    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext)context;
        initJobs();
    }


    @Autowired
    private ZookeeperRegistryCenter registryCenter;

//    @PostConstruct
    public void initJobs() {

        // 定义 SimpleJob 配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration
                .newBuilder("TestJob", "0/5 * * * * ?", 2)
                .shardingItemParameters("0=A,1=B")
                .build();
        JobTypeConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, TestJob.class.getCanonicalName());

        // 定义 LiteJob 配置
        LiteJobConfiguration simpleLiteJobConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();

        // 定义 JobScheduler 并启动
        ;
        ConfigurableListableBeanFactory factory = applicationContext.getBeanFactory();
        JobScheduler jobScheduler = new JobScheduler(registryCenter, simpleLiteJobConfig);
        jobScheduler.init();
        factory.registerSingleton("aaa",jobScheduler);
//        new JobScheduler(null, simpleLiteJobConfig).init();

    }

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter() {
        ZookeeperConfiguration config = new ZookeeperConfiguration("localhost:2181", "elastic-job-lite-spring-boot-demo");
        return new ZookeeperRegistryCenter(config);
    }


}