package com.weimj.core;

import java.util.Map;

/**
 * @Author:Weimj
 * @Date: 2023/5/24  0:06
 */
public interface RedisWrite {
    void  writeData(Map<String, Map<String, String>> data);

}
