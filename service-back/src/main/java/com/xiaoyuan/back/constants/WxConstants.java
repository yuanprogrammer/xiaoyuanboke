package com.xiaoyuan.back.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties(prefix = "wx")
public class WxConstants {

    private String appId;
    private String appSecret;
    private String callbackUrl;
    private String phoneBindingUrl;
    private String loginUrl;
    private String homeUrl;

    public static String APP_ID;
    public static String APP_SECRET;
    public static String CALLBACK_URL;
    public static String PHONE_BINDING_URL;
    public static String LOGIN_URL;
    public static String HOME_URL;

    @PostConstruct
    public void init() {
        APP_ID = this.appId;
        APP_SECRET = this.appSecret;
        CALLBACK_URL = this.callbackUrl;
        PHONE_BINDING_URL = this.phoneBindingUrl;
        LOGIN_URL = this.loginUrl;
        HOME_URL = this.homeUrl;
    }
}
