package com.xiaoyuan.front;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * FileName:    FrontApplication
 * Author:      小袁
 * Date:        2022/4/23 22:30
 * Description:
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.xiaoyuan.front.mapper"})
@ComponentScan({"com.xiaoyuan"})
@EnableFeignClients(basePackages = {"com.xiaoyuan"})
public class FrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
        System.out.println("========================== 小袁博客前台服务启动成功 ==========================");
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
