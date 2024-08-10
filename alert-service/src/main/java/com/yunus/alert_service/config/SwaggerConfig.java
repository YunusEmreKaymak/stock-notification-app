package com.yunus.alert_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

//http://localhost:8081/swagger-ui/index.html#
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${spring.application.name}") String title,
            @Value("${spring.application.description}") String description,
            @Value("${spring.application.version}") String version
    ) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(title)
                                .description(description)
                                .version(version)
                );
    }
}
