package com.xiaoyuan.front;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * FileName:    FrontApplication
 * Author:      小袁
 * Date:        2022/4/23 22:30
 * Description:
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.xiaoyuan.front.db.dao"})
@ComponentScan({"com.xiaoyuan"})
public class FrontApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
    }
}
