package com.rookies3.MySpringbootLab.config;

import org.springframework.context.annotation.*;

@Configuration
@Profile("test")
public class DevConfig {
    @Bean
    public MyEnvironment myEnvironment() {
        return new MyEnvironment("개발환경");
    }
}