package com.weimj.core.write;

import com.alibaba.fastjson.JSON;
import com.weimj.config.SchedulerInitializer;
import com.weimj.core.RedisWrite;
import com.weimj.entity.ProcessParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author:Weimj
 * @Date: 2023/5/24  0:13
 */
abstract class AbstractRedisWrite implements RedisWrite {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerInitializer.class);
    @Resource(name="localRedisTemplate")
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void writeData(ProcessParam param, List<Map<String, String>> data) {
        if (logger.isDebugEnabled()) {
            logger.debug("【{}】 writeData  data->[{}]", this.getClass(), JSON.toJSONString(data));
        }
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.openPipeline();
        write(param,connection,data);
        connection.closePipeline();
        connection.close();
    }
    public abstract void write(ProcessParam param,RedisConnection connection,List<Map<String, String>> data);

    /**
     * 将数据转换成协议写入 后续支持多种类型（如pb,gson等）
     * @param data
     * @return
     */
    public byte[] convertDataProtocol(Object data){
        return JSON.toJSONString(data).getBytes();
    }


}
