package com.xiaoyuan.back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * FileName:    BackApplication
 * Author:      小袁
 * Date:        2022/4/15 10:39
 * Description:
 */
@SpringBootApplication(scanBasePackages = {"com.xiaoyuan"})
@ComponentScan({"com.xiaoyuan"})
@MapperScan({"com.xiaoyuan.back.db.dao"})
@EnableAsync
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }
}
