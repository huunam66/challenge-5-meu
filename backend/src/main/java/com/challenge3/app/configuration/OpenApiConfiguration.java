package com.challenge3.app.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfiguration {

//    --- Dynamic Config ---

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPIConfig(
            @Value("${open.api.title}") String title,
            @Value("${open.api.version}") String version,
            @Value("${open.api.description}") String description,
            @Value("${open.api.license.name}") String licenseName,
            @Value("${open.api.license.url}") String licenceUrl,

            @Value("${open.api.servers.url.api}") String serverUrlApi,
            @Value("${open.api.servers.description}") String serverDescription
    ){
        return new OpenAPI().info(
            new Info()
                    .title(title)
                    .version(version)
                    .description(description)
                    .license(
                            new License()
                                    .name(licenseName)
                                    .url(licenceUrl)
                    )
        ).servers(
                List.of(
                        new Server()
                                .url(serverUrlApi)
                                .description(serverDescription)
        )).addSecurityItem(
                new SecurityRequirement()
                        .addList("Bearer Authentication")
        ).components(
                new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme())
        );
    }


    @Bean
    public GroupedOpenApi allApi(){
        return GroupedOpenApi.builder()
                .group("all-api")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi profileApi() {
        return GroupedOpenApi.builder()
                .group("profile-api")
//                .packagesToScan(ProductController.class.getPackageName())
                .pathsToMatch("/profile/**")
                .build();
    }


    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("products-api")
//                .packagesToScan(ProductController.class.getPackageName())
                .pathsToMatch("/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi locationApi() {
        return GroupedOpenApi.builder()
                .group("location-api")
                .pathsToMatch("/location/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("users-api")
//                .packagesToScan(UserController.class.getPackageName())
                .pathsToMatch("/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authorizeApi() {
        return GroupedOpenApi.builder()
                .group("authorize-api")
//                .packagesToScan(AuthController.class.getPackageName())
                .pathsToMatch("/auth/**")
                .build();
    }

}
