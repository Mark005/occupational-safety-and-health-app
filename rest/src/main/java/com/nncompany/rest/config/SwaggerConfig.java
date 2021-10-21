package com.nncompany.rest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${jwt.headerName}")
    private String headerName;

    @Bean
    public OpenAPI customOpenAPI(@Value("${spring.application.name}") String appName,
                                 @Value("${app.version}") String appVersion) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Application API")
                                .version(appVersion)
                                .description("API for " + appName)
                                .termsOfService("http://swagger.io/terms/")
                                .license(
                                        new License()
                                                .name("Apache 2.0")
                                                .url("https://springdoc.org/#springdoc-openapi-core-properties")))
                .components(
                        new Components()
                        .addSecuritySchemes("AUTHORIZATION",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name(headerName)
                                        .scheme("bearer")
                                        .description("Enter JWT Auth-Token **_only_**")
                                        .bearerFormat("JWT")));

    }
}
