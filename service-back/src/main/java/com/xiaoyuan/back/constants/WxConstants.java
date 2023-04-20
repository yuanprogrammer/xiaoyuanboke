package com.xiaoyuan.back.constants;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConstants implements InitializingBean {

    @Value("${wx.appId}")
    private String appId;

    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.callbackUrl}")
    private String callbackUrl;

    @Value("${wx.phoneBindingUrl}")
    private String phoneBindingUrl;

    @Value("${wx.loginUrl}")
    private String loginUrl;

    @Value("${wx.homeUrl}")
    private String homeUrl;

    public static String APP_ID;
    public static String APP_SECRET;
    public static String CALLBACK_URL;
    public static String PHONE_BINDING_URL;
    public static String LOGIN_URL;
    public static String HOME_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appId;
        APP_SECRET = this.appSecret;
        CALLBACK_URL = this.callbackUrl;
        PHONE_BINDING_URL = this.phoneBindingUrl;
        LOGIN_URL = this.loginUrl;
        HOME_URL = this.homeUrl;
    }
}
