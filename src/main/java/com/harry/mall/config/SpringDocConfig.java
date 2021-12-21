package com.harry.mall.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpringDocConfig {

  @Value("${springdoc.securityScheme.name}")
  private String securitySchemeKey;

  @Bean
  public OpenAPI mall() {
    return new OpenAPI()
        .info(new Info().title("mall-swagger").description("mall app").version("1.0"))
        .components(
            new Components()
                .addSecuritySchemes(
                    securitySchemeKey,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeKey, Arrays.asList("read","write")));
  }
}
