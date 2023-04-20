package com.xiaoyuan.system.log.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * @author 袁
 * @company 微科云-MiscroCloud
 * </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiscroCloudSystemLog implements Serializable {

    private static final long serialVersionUID = 1L;

    // 线程id
    private String threadId;
    // 线程名称
    private String threadName;
    // ip
    private String ip;
    // url
    private String url;
    // http方法 GET POST PUT DELETE PATCH
    private String requestMethodType;
    // 类方法
    private String classMethodName;
    // 请求参数
    private Object requestParams;
    // 返回参数
    private Object result;
    // 业务状态码
    private Integer serviceStatusCode;
    // 响应状态码
    private Integer statusCode;
    // 接口耗时
    private Long timeCost;
    // 操作系统
    private String os;
    // 浏览器
    private String browser;
    // user-agent
    private String userAgent;


    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
