package com.weimj.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.weimj.core.Process;
import com.weimj.entity.ProcessParam;
import com.weimj.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TestJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {


    }
}
