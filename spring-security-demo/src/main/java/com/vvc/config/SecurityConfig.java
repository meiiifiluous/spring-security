package com.vvc.config;

import com.vvc.filter.JwtAuthenticationTokenFilter;
import com.vvc.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 密码加密
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userService;

    //设置密码加密为B加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    // 定义一个名为securityFilterChain的bean，该bean将负责构建和应用安全过滤器链
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //开发和测试阶段禁用CSRF
        http.csrf(csrf -> csrf.disable());
        // 配置HttpSecurity对象，定义安全规则
        http
                // 授权HTTP请求，定义哪些URL需要什么类型的访问控制
                .authorizeHttpRequests((auth) -> auth
                        // 允许"/user/login" URL匿名访问
                        .requestMatchers("/user/login").permitAll()
                        // 所有其他请求都需要认证才能访问
                        .anyRequest().authenticated())


                // 启用HTTP Basic认证，默认情况下提供简单的用户名/密码认证
                .httpBasic(Customizer.withDefaults())
                //把token校验过滤器添加到过滤器链中
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 构建并返回SecurityFilterChain
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
