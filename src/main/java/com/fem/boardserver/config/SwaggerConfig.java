package com.fem.boardserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenAPI() {
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Access Token");

        SecurityScheme accessToken = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        Components components = new Components()
                .addSecuritySchemes("Access Token", accessToken);

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
