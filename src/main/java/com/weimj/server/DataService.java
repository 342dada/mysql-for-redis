package com.weimj.server;

import com.weimj.entity.ProcessParam;
import java.util.List;
import java.util.Map;

/**
 * 获取数据
 * 后续可提供spi，预留扩展点
 * @Author:Weimj
 * @Date: 2023/5/27  14:26
 */
public interface DataService {
    /**
     * 获取数据源
     * @param param
     * @return
     */
    List<Map<String, String>> getData(ProcessParam param);
}
