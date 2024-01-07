package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.blog.interceptors.ContactInformationInterceptor;
import com.blog.interceptors.GetUserInterceptor;
import com.blog.interceptors.SidebarArticleInterceptor;

@Configuration
@PropertySource("file:${user.dir}/.env")
public class AppConfig implements WebMvcConfigurer {
    // Các cấu hình khác

    @Autowired
    private GetUserInterceptor getUserInterceptor;

    @Autowired
    private ContactInformationInterceptor contactInformationInterceptor;

    @Autowired
    private SidebarArticleInterceptor sidebarArticleInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getUserInterceptor);
        registry.addInterceptor(contactInformationInterceptor);
        registry.addInterceptor(sidebarArticleInterceptor);
    }
}
