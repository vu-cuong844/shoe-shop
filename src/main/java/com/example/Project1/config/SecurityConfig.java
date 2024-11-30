package com.example.Project1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.Project1.service.UserService;

/**
 * SecurityConfig
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private @Lazy UserService userService;

    @Autowired
    private CutomerAuthenticationSuccessHandler cutomerAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/home").hasAnyAuthority("USER_1","ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/account").hasAnyAuthority("USER_1","ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/updateAccount").hasAnyAuthority("USER_1")
                        .requestMatchers(HttpMethod.GET, "/user/reject").hasAnyAuthority("USER_0")
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/shipper/**").hasAnyAuthority("SHIPPER")
                        // .requestMatchers("/user/**").hasAnyAuthority("USER_1")
                        .requestMatchers(HttpMethod.GET, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers("/user/**").hasAnyAuthority("USER_1")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/user/login") 
                        // .loginProcessingUrl("/user/login")
                        .successHandler(cutomerAuthenticationSuccessHandler)
                        .failureUrl("/user/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/user/login")
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}