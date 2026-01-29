package com.wl.security_demo.config;

import com.wl.security_demo.filters.JwtFilter;
import com.wl.security_demo.security.LogoutSuccessHandlerImpl;
import com.wl.security_demo.security.LogoutHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 开启方法级权限注解 @PreAuthorize
public class SecurityConfig {

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private LogoutHandlerImpl logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtFilter jwtFilter,
                                           AuthenticationEntryPoint authEntryPoint,
                                           AccessDeniedHandler deniedHandler) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 禁用CSRF（API服务通常不需要）
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/register", "/error").permitAll()// 允许匿名访问登录接口
                        .anyRequest().authenticated()          // 其他所有请求必须认证
                )
                // 异常处理
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint) // 401
                        .accessDeniedHandler(deniedHandler)       // 403
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 登出接口路径
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(logoutSuccessHandler)
                )
                // 关键：在用户名密码过滤器之前添加我们的 JWT 过滤器
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        ;
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}