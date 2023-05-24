package com.weimj.core;

import com.weimj.entity.ProcessParam;
import com.weimj.mapper.TaskMapper;
import com.weimj.tag.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class JobProcess   {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private  List<RedisWrite> redisWriteList;

    public void Process(ProcessParam param){
        RedisWrite redisWrite = searchRedisWrite(param.getDataFormat());
        List<Map<String, String>> data = getData(param);
        redisWrite.writeData(param,data);
    }

    //后续可提供spi织入点
    private List<Map<String, String>> getData(ProcessParam param){
        String sql = param.getSql();
        Long count = taskMapper.count(sql);
        return taskMapper.select(param.getSql());
    }

    private RedisWrite searchRedisWrite(String typeName) {
        for (RedisWrite redisWrite : redisWriteList) {
            Type typeAnnotation = AnnotationUtils.findAnnotation(redisWrite.getClass(), Type.class);
            if (typeAnnotation != null) {
                String name = typeAnnotation.name();
                if (Objects.equals(name, typeName)) {
                    return redisWrite;
                }
            }
        }
        return searchRedisWrite("STRING");
    }






}
