package com.xiaoyuan.three;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.xiaoyuan"})
@EnableFeignClients
public class ThreePartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreePartyApplication.class, args);
        System.out.println("========================== 第三方服务启动成功 ==========================");
    }
}
