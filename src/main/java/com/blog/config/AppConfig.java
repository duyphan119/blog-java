package com.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${user.dir}/.env")
public class AppConfig {
    // Các cấu hình khác
}
