package com.weimj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weimj.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskMapper extends BaseMapper {
   @Select("select * from (${sql}) b")
    List<Map<String,String>> select(String sql);

    @Select("select count(0) from (${sql}) b")
    Long count(String sql);

}