package com.ox.banking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SpringDocConfig {
    
    @Bean
    public OpenAPI bankingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Banking API")
                        .description("Banking System API Documentation")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Banking Team")
                                .email("support@banking.com")));
    }
}