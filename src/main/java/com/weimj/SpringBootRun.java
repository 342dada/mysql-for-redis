package com.weimj;

import com.weimj.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringBootRun {


    @Autowired
    private TaskMapper taskMapper;
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRun.class,args);
    }
    @PostConstruct
    private void a (){
//        Task task = taskMapper.selectById(23L);
//        System.out.println(task);
    }

}
