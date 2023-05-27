package com.weimj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskMapper{
   @Select("select * from (${sql}) b")
    List<Map<String,String>> select(String sql);
    @Select("select * from (${sql}) b limit  #{startIndex} ,#{pageSize}")
    List<Map<String,String>> select(@Param("sql") String sql, @Param("startIndex") long startIndex,@Param("pageSize") int pageSize);
    @Select("select count(0) from (${sql}) b")
    int count(String sql);

}