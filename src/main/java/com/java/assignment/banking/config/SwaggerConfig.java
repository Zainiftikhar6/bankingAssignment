package com.java.assignment.banking.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Banking Assignment")
                        .description("Banking Assignment")
                        .version("0.0.1-SNAPSHOT")
                        .license(new License().name("PROPRIETARY")))
                .externalDocs(new ExternalDocumentation()
                        .description(
                                "Banking Assignment")
                        .url("zainiftikhar666@gmail.com"));
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter().in("header").required(true).description("user").name("user").schema(new StringSchema()));
    }
}

