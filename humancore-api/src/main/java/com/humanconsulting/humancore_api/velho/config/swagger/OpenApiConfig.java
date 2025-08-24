package com.humanconsulting.humancore_api.velho.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Projeto Human Consulting",
                description = "Sistema de consultoria de gestão empresarial",
                contact = @Contact(
                        name = "Human Consulting",
                        url = "https://humanconsulting.com.br/",
                        email = "contato@humanconsulting.com.br"
                ),
                license = @License(name = "UNLICENSED", url = "https://unlicense.org/"),
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = "Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Insira o token JWT no formato: Bearer {seu_token}"
)
public class OpenApiConfig {
}
