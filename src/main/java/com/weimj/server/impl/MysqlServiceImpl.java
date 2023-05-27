package com.weimj.server.impl;

import com.google.common.collect.Lists;
import com.weimj.core.JobProcess;
import com.weimj.entity.ProcessParam;
import com.weimj.mapper.TaskMapper;
import com.weimj.server.MysqlDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author:Weimj
 * @Date: 2023/5/27  14:24
 */
@Service("mysqlServiceImpl")
public class MysqlServiceImpl implements MysqlDataService {
    private static final Logger logger = LoggerFactory.getLogger(JobProcess.class);

    private ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(2, 16, 100L, TimeUnit.SECONDS, new ArrayBlockingQueue(32), new ThreadPoolExecutor.CallerRunsPolicy());

    private static int PAGE_SIZE = 10;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Map<String, String>> getData(ProcessParam param) {
        String sql = param.getSql();
        int count = taskMapper.count(sql);
        //分片查询，避免oom
        return Optional.ofNullable(count)
                .filter(c -> c > PAGE_SIZE)
                .map(c -> {
                    int frequency = c / PAGE_SIZE;
                    List<CompletableFuture<List<Map<String, String>>>> futures =
                            //生成0~
                            IntStream.range(0, frequency + 1)
                                    .mapToObj(i -> i * PAGE_SIZE)
                                    .map(startIndex -> CompletableFuture.supplyAsync(() ->
                                                    taskMapper.select(sql, startIndex, PAGE_SIZE)
                                            , THREAD_POOL)
//                                            .exceptionally(ex -> {
//                                                //异常不处理,只打印日志
//                                                logger.error("getData run error,sql->{} ,startIndex:{}", sql, startIndex);
//                                                return Lists.newArrayList();
//                                            })
                                    )
                                    .collect(Collectors.toList());

                    return futures.stream()
                            .map(CompletableFuture::join)
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
                })
                .orElseGet(() -> taskMapper.select(sql, 0, count));
    }
}
