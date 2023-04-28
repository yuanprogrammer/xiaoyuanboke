//package com.xiaoyuan.system.exception.swagger;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * FileName:    SwaggerConfig
// * Author:      小袁
// * Date:        2022/3/11 19:09
// * Description:
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    @Bean
//    public Docket webApiConfig(){
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("webApi")
//                .apiInfo(webApiInfo())
//                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
//                .paths(Predicates.not(PathSelectors.regex("/error.*")))
//                .build();
//
//    }
//
//    private ApiInfo webApiInfo(){
//
//        return new ApiInfoBuilder()
//                .title("Swagger接口测试")
//                .description("小袁同学")
//                .version("1.0")
//                .contact(new Contact("Helen", "http://www.baidu.com", "1971788445@qq.com"))
//                .build();
//    }
//}
