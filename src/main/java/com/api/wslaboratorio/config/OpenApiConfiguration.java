package com.api.wslaboratorio.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {@Server(
                url = "/api"//,
               // description = "Servidor Aplicación Laboratorio"
        )},
        info = @Info(
                title = "TFM",
                description = "Está api rest tiene como objetivo servir de manera controlada la información para la administración" +
                        " de una empresa que gestiona citas de exámenes de laboratorio.",
                version = "${api.version}",
                contact = @Contact(
                        name = "Equipo G3023",
                        email = "desarrollo-sis@gmail.com",
                        url = "https://laboratorio.edu.ni"
                ),
                license = @License(
                        name = "(c) UNIR"
                ),
                termsOfService = "Términos de Servicio"
        )
)
public class OpenApiConfiguration {
    private final String SCHEME_SECURITY_AUTH = "Authorization Bearer";
    private final String SCHEME_AUTH = "bearer";
    private final String AUTH_FORMAT = "JWT";

    @Bean
    public OpenAPI openAPI() {
        String securitySchemeName = this.SCHEME_SECURITY_AUTH;
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme().name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP).scheme(this.SCHEME_AUTH).bearerFormat(this.AUTH_FORMAT)
                ));
    }
}