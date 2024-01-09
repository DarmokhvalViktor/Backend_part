package com.darmokhval.Backend_part.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myApiDocumentation() {
        return new OpenAPI()
                .info(new Info()
                        .title("Application version: 2024 January (08)")
                        .description("Backend part for site with tests")
                        .version("1"));
    }
}
