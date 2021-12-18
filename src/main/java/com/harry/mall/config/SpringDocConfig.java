package com.harry.mall.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI mall() {
        return new OpenAPI()
                .info(new Info().title("mall-swagger")
                .description("mall app")
                .version("1.0"));
    }

}
