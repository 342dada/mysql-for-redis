package com.weimj.core.write;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * @Author:Weimj
 * @Date: 2023/5/24  0:10
 */
@Component
public class RedisWriteList extends AbstractRedisWrite {

    @Override
    public List<Object> write(Pipeline pipelined, Map<String, Map<String, String>> data) {
        data.forEach((k,v)->{
            pipelined.lpush(processParam.getKey(),JSON.toJSONString(v));
        });
        return pipelined.syncAndReturnAll();
    }
}
