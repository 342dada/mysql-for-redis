package com.weimj.job;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.google.gson.Gson;
import com.weimj.core.JobProcess;
import com.weimj.entity.ProcessParam;

import java.text.SimpleDateFormat;
import java.util.Date;
public class TestJob implements SimpleJob  {
    private  JobProcess bean;
    @Override
    public void execute(ShardingContext shardingContext) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        String currentDate = sdf.format(new Date());
        System.out.println("Current Date: " + currentDate);
        System.out.println(JSON.toJSONString(shardingContext.getJobParameter()));
        Gson gson = new Gson();
        ProcessParam processParam = gson.fromJson(shardingContext.getJobParameter(), ProcessParam.class);
        bean.Process(processParam);
    }

    /**
     * 构造器注入
     * @param bean
     */
    public TestJob(JobProcess bean){
        //初始化bean
        this.bean=bean;
    }

}
