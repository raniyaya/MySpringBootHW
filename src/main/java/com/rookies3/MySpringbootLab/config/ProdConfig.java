package com.rookies3.MySpringbootLab.config;

import org.springframework.context.annotation.*;

@Configuration
@Profile("prod")
public class ProdConfig {
    @Bean
    public MyEnvironment myEnvironment() {
        return new MyEnvironment("운영환경");
    }
}