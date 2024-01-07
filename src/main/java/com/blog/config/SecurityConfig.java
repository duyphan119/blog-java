package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.blog.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private final String[] PUBLIC_URL_LIST = { "/", "/contact", "/article/**", "/api/**" };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URL_LIST)
                        .permitAll()
                        .requestMatchers("/admin/**")
                        .hasAnyAuthority("ADMIN")
                        .anyRequest()
                        .authenticated())
                .formLogin(login -> login
                        .loginPage("/logon")
                        .loginProcessingUrl("/logon")
                        .usernameParameter("username")
                        .passwordParameter("hashedPassword")
                        .defaultSuccessUrl("/admin", true))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logon")
                        .permitAll())
                .build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(true).ignoring().requestMatchers("/static/**", "/assets/**");
    }

    @Bean
    BCryptPasswordEncoder encryptPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(username -> customUserDetailsService.loadUserByUsername(username));
        authProvider.setPasswordEncoder(encryptPassword());
        return authProvider;
    }
}
