package com.g41.trashsmart_server.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "TrashSmart API",
                version = "1.0",
                description = "API documentation for TrashSmart application",
                contact = @Contact(
                        name = "Lasith Ranahewa",
                        email = "lasithranahewa@gmail.com"
                ),
                license = @License(
                        name = "GPL-3.0 license",
                        url = "https://www.gnu.org/licenses/"
                ),
                termsOfService = "Term of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "Production ENV",
                        url = "https://trashsmart.koyeb.app"
                )
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
        description = "JWT auth description"
)
public class SwaggerConfig {
}