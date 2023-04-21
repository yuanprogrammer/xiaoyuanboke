package com.xiaoyuan.back.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: YDW
 * @Date: 2023-04-21 16:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String urlPrefix;

    public static String ACCESS_KEY;
    public static String SECRET_KEY;
    public static String BUCKET;
    public static String URL_PREFIX;

    @PostConstruct
    public void init() {
        ACCESS_KEY = this.accessKey;
        SECRET_KEY = this.secretKey;
        BUCKET = this.bucket;
        URL_PREFIX = this.urlPrefix;
    }
}
