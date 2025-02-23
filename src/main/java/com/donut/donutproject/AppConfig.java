package com.donut.donutproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig { // 또는 다른 설정 클래스 이름

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
