package com.challenge3.app.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfiguration {

//    --- Dynamic Config ---

    @Bean
    public OpenAPI openAPIConfig(
            @Value("${open.api.title}") String title,
            @Value("${open.api.version}") String version,
            @Value("${open.api.description}") String description,
            @Value("${open.api.license.name}") String licenseName,
            @Value("${open.api.license.url}") String licenceUrl,

            @Value("${open.server.localhost.url}") String localhostURL,
            @Value("${open.server.localhost.description}") String localhostDescription
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
                            .url(localhostURL)
                            .description(localhostDescription)
        ));
    }

//    @Bean
//    public GroupedOpenApi apiAll() {
//        return GroupedOpenApi.builder()
//                .group("api-all")
//                .packagesToScan("com.challenge3.app.domain")
//                .build();
//    }


    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group("api-products")
                .packagesToScan("com.challenge3.app.domain.product.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("api-users")
                .packagesToScan("com.challenge3.app.domain.user.controller.user")
                .build();
    }

    @Bean
    public GroupedOpenApi authorizeApi() {
        return GroupedOpenApi.builder()
                .group("api-authorize")
                .packagesToScan("com.challenge3.app.domain.user.controller.auth")
                .build();
    }

//    --- Static Config ---
//    @Bean
//    public OpenAPI openAPIConfig(){
//        return new OpenAPI().info(
//                new Info()
//                        .title("API Challenge-5-meu documentation")
//                        .version("v2.6.0")
//                        .description("API Challenge-5-meu")
//                        .license(
//                                new License()
//                                        .name("Api license")
//                                        .url("https://github.com/challenge3/challenge-5-meu")
//                        )
//        ).servers(
//                List.of(
//                        new Server()
//                                .url("http://localhost:8080")
//                                .description("server test")
//                )
//        );
//    }
}
