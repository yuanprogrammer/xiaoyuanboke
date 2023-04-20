package com.xiaoyuan.system.log.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitAutoConfiguration {

    @Bean
    @ConditionalOnClass(InitSystemLogConfiguration.class)
    public InitSystemLogConfiguration initSystemLogConfiguration() {
        return new InitSystemLogConfiguration();
    }
}
