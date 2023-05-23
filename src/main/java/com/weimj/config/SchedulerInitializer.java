package com.weimj.config;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * SchedulerInitializer
 * 辅助管理JobScheduler 统一开启关闭
 *
 * @Author:Weimj
 * @Date: 2023/5/22  22:56
 */
@Data
public class SchedulerInitializer {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerInitializer.class);

    private List<JobScheduler> jobSchedulers;

    /**
     * @param jobSchedulers
     */
    public SchedulerInitializer(List<JobScheduler> jobSchedulers) {
        this.jobSchedulers = jobSchedulers;
    }

    public void startAllSchedulers() {
        for (JobScheduler jobScheduler : jobSchedulers) {
            jobScheduler.init();
        }
    }

    public void shutdownAllSchedulers() {
        for (JobScheduler jobScheduler : jobSchedulers) {
            jobScheduler.getSchedulerFacade().shutdownInstance();
        }
        logger.info("All tasks for job '{}' have been closed successfully.", JSON.toJSONString(jobSchedulers));

    }

    public void init() {
        startAllSchedulers();
    }
}
