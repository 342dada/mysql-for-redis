package com.weimj.core;

import com.alibaba.fastjson.JSON;
import com.weimj.entity.ProcessParam;
import com.weimj.mapper.TaskMapper;
import com.weimj.utils.MatcherUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RestController
public class Process   {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
//    public void Process(ProcessParam processParam){
    @GetMapping(path = "/a")
    public void Process(){
//        String    sql="select start_date startDate,id ,end_date endData from task";
        String    sql="select * from user_open";
        ProcessParam param = new ProcessParam();
        Long count = taskMapper.count(sql);
        List<Map<String, String>> select = taskMapper.select(sql);
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        valueOperations.set("aaa","bbb");
        String template = "{id}_{end_date}";

        Map<String, Map<String, String>> collect = select.stream().collect(Collectors.toMap(map -> MatcherUtils.extractVariables(template, map), map -> map,(existing, replacement) -> existing));

        System.out.println(JSON.toJSONString(collect));


        //        String test = select.get(0).get("test");
    }

    public void  writeToReidsString(Map<String, Map<String, String>> data,ProcessParam processParam){
        Jedis jedisPoolResource = jedisPool.getResource();
        Pipeline pipelined = jedisPoolResource.pipelined();
        data.forEach((k,v)->{
            pipelined.set(k,JSON.toJSONString(v));
        });
        pipelined.sync();
    }

    public void  writeToReidsList(Map<String, Map<String, String>> data ,ProcessParam processParam){
        Jedis jedisPoolResource = jedisPool.getResource();
        Pipeline pipelined = jedisPoolResource.pipelined();
        data.forEach((k,v)->{
            pipelined.lpush(processParam.getKey(),JSON.toJSONString(v));
        });
        pipelined.sync();
    }
    public void  writeToReidsSet(Map<String, Map<String, String>> data ,ProcessParam processParam){
        Jedis jedisPoolResource = jedisPool.getResource();
        Pipeline pipelined = jedisPoolResource.pipelined();
        data.forEach((k,v)->{
            pipelined.sadd(processParam.getKey(),JSON.toJSONString(v));
        });
        pipelined.sync();
    }

    public void  writeToReidsMap(Map<String, Map<String, String>> data ,ProcessParam processParam){
        Jedis jedisPoolResource = jedisPool.getResource();
        Pipeline pipelined = jedisPoolResource.pipelined();
        data.forEach((k,v)->{
            pipelined.hset(processParam.getKey(),k,JSON.toJSONString(v));
        });
        pipelined.sync();
    }
    public void  writeToReidsZset(Map<String, Map<String, String>> data ,ProcessParam processParam){
        Jedis jedisPoolResource = jedisPool.getResource();
        Pipeline pipelined = jedisPoolResource.pipelined();
        data.forEach((k,v)->{
            //默认取id排序
            pipelined.zadd(processParam.getKey(),Double.valueOf(Optional.ofNullable(processParam.getZsetScore()).orElse(v.get("id"))),JSON.toJSONString(v));
        });
        pipelined.sync();
    }



    private void a (){
        ProcessParam param = new ProcessParam();
    }

//    @Override
    public void run(ApplicationArguments args) throws Exception {
        Process();
    }
}
