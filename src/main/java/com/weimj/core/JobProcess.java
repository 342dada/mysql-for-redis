package com.weimj.core;

import com.weimj.constant.WriteRedisConstant;
import com.weimj.entity.ProcessParam;
import com.weimj.server.DataService;
import com.weimj.tag.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class JobProcess   {
    @Resource(name="mysqlServiceImpl")
    private DataService mysqlServiceImpl;

    @Autowired
    private  List<RedisWrite> redisWriteList;

    public void Process(ProcessParam param){
        RedisWrite redisWrite = searchRedisWrite(param.getDataFormat());
        List<Map<String, String>> data = mysqlServiceImpl.getData(param);
        redisWrite.writeData(param,data);
    }

    /**
     * 根据type寻找对应的写入redis执行器，如果找不到，默认使用string
     * @param typeName
     * @return
     */
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
        return searchRedisWrite(WriteRedisConstant.STRING);
    }






}
