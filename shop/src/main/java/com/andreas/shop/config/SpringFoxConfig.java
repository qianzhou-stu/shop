package com.andreas.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//public class SpringFoxConfig {
//
//    //访问http://localhost:8083/swagger-ui.html可以看到API文档
//    @Bean
//    public Docket api() {
////        ParameterBuilder paramBuilder = new ParameterBuilder(); //创建参数构建对象
////        List<Parameter> paramList= new ArrayList<Parameter>();
////        paramBuilder.name("limit").description("分页")
////                .modelRef(new ModelRef("string")).parameterType("query")
////                .required(false).build();
////        paramList.add(paramBuilder.build()); //添加到集合中
////        paramBuilder.name("page").description("页显示数");
////        paramList.add(paramBuilder.build());
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.any())
////                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
////                .apis(RequestHandlerSelectors.basePackage("基包名"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("生鲜商城")
//                .description("")
//                .termsOfServiceUrl("")
//                .build();
//    }
//}

@Configuration
public class SpringFoxConfig {

    //访问http://localhost:8083/swagger-ui.html可以看到API文档  --- 模板化
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("生鲜商城")
                .description("基于Spring boot开发的生鲜商城系统")
                .termsOfServiceUrl("")
                .build();
    }
}