package com.weimj.core.write;

import com.alibaba.fastjson.JSON;
import com.weimj.constant.WriteRedisConstant;
import com.weimj.entity.ProcessParam;
import com.weimj.tag.Type;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * @Author:Weimj
 * @Date: 2023/5/24  0:10
 */
@Component
@Type(name= WriteRedisConstant.SET)
public class RedisWriteSet extends AbstractRedisWrite {

    @Override
    public List<Object> write(ProcessParam param, RedisConnection redisConnection, List<Map<String, String>> data) {
        String keyTemplate = param.getKeyTemplate();
        data.forEach(map->redisConnection.set(keyTemplate.getBytes(), convertDataProtocol(map)));
        return null;
    }
}
