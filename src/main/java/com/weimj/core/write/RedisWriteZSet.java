package com.weimj.core.write;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Longs;
import com.weimj.constant.WriteRedisConstant;
import com.weimj.entity.ProcessParam;
import com.weimj.tag.Type;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author:Weimj
 * @Date: 2023/5/24  0:10
 */
@Component
@Type(name= WriteRedisConstant.ZSET)
public class RedisWriteZSet extends AbstractRedisWrite {
    /**
     * zset会取指定字段的值排序,指定字段取不到取id,id取不到取结果集顺序排
     * @param param
     * @param
     * @param data
     * @return
     */

    @Override
    public List<Object> write(ProcessParam param, RedisConnection redisConnection, List<Map<String, String>> data){
        String keyTemplate = param.getKeyTemplate();
        String column = param.getZsetScoreColumn();
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> map=data.get(i);
            String scoreStr = map.get(column);
            Long score = Longs.tryParse(scoreStr);
            if(Objects.isNull(score)){
                //配置字段取不到取id
                score=Longs.tryParse(map.get("id"));
            }
            //id取不到,按结果集排序
            redisConnection.lSet(keyTemplate.getBytes(), Objects.isNull(score)?i:score,convertDataProtocol(map));
        }

        return null;
    }
}
