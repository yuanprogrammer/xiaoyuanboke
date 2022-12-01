package com.xiaoyuan.system.log.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

public class InitSystemLogConfiguration implements InitializingBean {

    private static Logger log = LoggerFactory.getLogger(InitSystemLogConfiguration.class);

    @Value(value = "${miscrocloud.system.global-log.disable:true}")
    private boolean globalLogDisable;

    @Value(value = "${miscrocloud.system.global-log.time-cost-detect-disable:false}")
    private boolean timeCostDetectDisable;

    @Value(value = "${miscrocloud.system.global-log.time-cost-detect:3000}")
    private int timeCostDetect;

    @Value(value = "${miscrocloud.system.global-log.service-response-disable:false}")
    private boolean serviceResponseDisable;

    @Value(value = "${miscrocloud.system.global-log.service-response-status-name:code}")
    private String serviceResponseStatusName;

    @Value(value = "${miscrocloud.system.global-log.service-response-status-value:200}")
    private String serviceResponseStatusValue;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (globalLogDisable) {
            log.info("==================================================");
            log.info("MiscroCloud 系统全局日志开启");
            log.info("==================================================");
        }
        if (timeCostDetectDisable) {
            log.info("==================================================");
            log.info("miscrocloud 接口超时检测日志开启");
            log.info("接口超时检测时长：{}ms", timeCostDetect);
            log.info("==================================================");
        }
        if (serviceResponseDisable) {
            log.info("==================================================");
            log.info("MiscroCloud 业务错误日志开启");
            log.info("业务检测字段：{}", serviceResponseStatusName);
            log.info("业务正确状态码：{}", serviceResponseStatusValue);
            log.info("==================================================");
        }
    }
}
