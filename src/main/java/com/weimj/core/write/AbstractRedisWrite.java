package com.weimj.core.write;

import com.alibaba.fastjson.JSON;
import com.weimj.config.SchedulerInitializer;
import com.weimj.core.RedisWrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * @Author:Weimj
 * @Date: 2023/5/24  0:13
 */
abstract class AbstractRedisWrite implements RedisWrite {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerInitializer.class);

    @Autowired

    private JedisPool jedisPool;

    @Override
    public void writeData(Map<String, Map<String, String>> data) {
        if (logger.isDebugEnabled()) {
            logger.debug("【{}】 writeData  data->[{}]", this.getClass(), JSON.toJSONString(data));
        }
        Jedis jedis = jedisPool.getResource();
        Pipeline pipelined = jedis.pipelined();
        write(pipelined,data);
    }
    public abstract List<Object> write(Pipeline pipelined,Map<String, Map<String, String>> data);
}
