package com.example.taskmaster.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TaskMaster API")
                        .version("1.0")
                        .description("API para gerenciamento de tarefas")
                        .contact(new Contact()
                                .name("Mateus")
                                .email("sec.mateuslgomes@gmail.com")
                                .url("https://github.com/mlgsec"))
                );
    }
}