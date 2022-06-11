package com.xiaoyuan.qiniu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * FileName:    QiniuApplication
 * Author:      小袁
 * Date:        2022/4/4 15:53
 * Description:
 */
// exclude忽略数据源的自动配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.xiaoyuan"})
public class QiniuApplication {

    public static void main(String[] args) {
        SpringApplication.run(QiniuApplication.class, args);
    }
}
