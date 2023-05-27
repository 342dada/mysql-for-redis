package com.weimj.core.write;

import com.alibaba.fastjson.JSON;
import com.weimj.constant.WriteRedisConstant;
import com.weimj.entity.ProcessParam;
import com.weimj.tag.Type;
import com.weimj.utils.MatcherUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 将整个结果集写一个固定的key
 * @Author:Weimj
 * @Date: 2023/5/24  0:10
 */
@Component
@Type(name= WriteRedisConstant.HASH_ALL)
public class RedisWriteHashAll extends AbstractRedisWrite {
    @Override
    public void write(ProcessParam param, RedisConnection redisConnection, List<Map<String, String>> data) {
        String keyTemplate = param.getKeyTemplate();
        String fieldTemplate = param.getFieldTemplate();
        //@TODO 如果hset 单个field写入,会导致多个命令提交，不够高效, 如果hset整个map,如果map太大，会影响redis性能,并且本地需要将data转换成map,浪费内存.具体看场景取舍，后续支持多场景
//        data.forEach(map->{
//            pipelined.hset(keyTemplate,MatcherUtils.extractVariables(fieldTemplate, map), convertDataProtocol(map));
//        });
        //重复任选一个
        Map<byte[], byte[]> resultMap = data.stream().collect(Collectors.toMap(map -> MatcherUtils.extractVariables(fieldTemplate, map).getBytes(), map -> convertDataProtocol(map), (existing, replacement) -> convertDataProtocol(existing)));
        redisConnection.hMSet(keyTemplate.getBytes(),resultMap);
    }

}
