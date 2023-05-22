package com.weimj.entity;

import lombok.Data;

/**
 * @Author:Weimj
 * @Date: 2023/5/14  23:50
 */
@Data
public class ProcessParam {
    /**
     * 执行sql
     */
    private String sql;
    /**
     * 写入redis key
     */
    private String key;
    /**
     * 写入redis value
     */
    private String value;
    /**
     * 写入redis 数据格式
     */
    private String dataFormat;

    private String zsetScore;

    private String cron;

    private String jobName;

    private String jobParam;
}
