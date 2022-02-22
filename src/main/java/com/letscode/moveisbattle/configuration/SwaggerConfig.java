package com.letscode.moveisbattle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket moviesBattleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.letscode.moveisbattle"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Movies Battle API REST",
                "API REST - Desafio Lets Code.",
                "1.0",
                "Terms of Service",
                new Contact("Igor Mascarenhas", "https://www.linkedin.com/in/igor-mascarenhas/",
                        "igorsmascarenhas@gmail.com"),
                "License - MIT", "https://opensource.org/licenses/MIT", new ArrayList<>()
        );

        return apiInfo;
    }
}
