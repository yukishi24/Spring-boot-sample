package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers("/webjars/**").requestMatchers("/css/**")
        .requestMatchers("/js/**").requestMatchers("/h2-console/**");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login.loginProcessingUrl("/login") // ログイン処理のパス
        .loginPage("/login") // ログインページ
        .failureUrl("/login?error") // 失敗時
        .usernameParameter("userId").passwordParameter("password")
        .defaultSuccessUrl("/userList", true))
        .authorizeHttpRequests(authz -> authz.requestMatchers("/login").permitAll() // 直リンクOK
            .requestMatchers("/user/signup").permitAll() // 直リンクOK
            .anyRequest().authenticated());
    http.csrf().disable();
    return http.build();
  }
}
