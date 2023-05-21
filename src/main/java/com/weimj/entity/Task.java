package com.weimj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("Task")
public class Task {
    private Long id;
    private Date startDate;
    private Date endDate;
    private String operatorId;
}
