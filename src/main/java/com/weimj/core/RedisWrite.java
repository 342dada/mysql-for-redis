package com.weimj.core;

import com.weimj.entity.ProcessParam;

import java.util.List;
import java.util.Map;

/**
 * @Author:Weimj
 * @Date: 2023/5/24  0:06
 */
public interface RedisWrite {
    void  writeData(ProcessParam param, List<Map<String, String>> data);

}
