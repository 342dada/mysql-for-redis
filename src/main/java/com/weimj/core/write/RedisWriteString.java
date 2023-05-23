package com.weimj.core.write;

import com.alibaba.fastjson.JSON;
import com.weimj.core.RedisWrite;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * @Author:Weimj
 * @Date: 2023/5/24  0:10
 */
@Component
public class RedisWriteString extends AbstractRedisWrite {

    @Override
    public List<Object> write(Pipeline pipelined, Map<String, Map<String, String>> data) {
        data.forEach((k,v)->pipelined.set(k, JSON.toJSONString(v)));
        return pipelined.syncAndReturnAll();
    }
}
