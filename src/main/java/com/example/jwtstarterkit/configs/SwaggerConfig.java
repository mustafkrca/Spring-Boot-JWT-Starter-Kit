package com.example.jwtstarterkit.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JWT Starter Kit API")
                        .description("Bu API, JWT tabanlı kimlik doğrulama ile çalışan bir starter kit için geliştirilmiştir.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Destek Ekibi")
                                .email("support@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Geliştirme Ortamı"),
                        new Server().url("https://api.example.com").description("Üretim Ortamı")
                ));
    }
}
