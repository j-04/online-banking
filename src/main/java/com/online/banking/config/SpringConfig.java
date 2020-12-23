package com.online.banking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Online banking").description("REST-сервисы с описанием параметров"));
    }

}
