package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

  /**
   * セキュリティの対象外の設定
   * 
   * @return
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers("/webjars/**").requestMatchers("/css/**")
        .requestMatchers("/js/**").requestMatchers("/h2-console/**");
  }

  /**
   * セキュリティの各種設定
   * 
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login.loginProcessingUrl("/login") // ログイン処理のパス
        .loginPage("/login") // ログインページ
        .failureUrl("/login?error") // 失敗時
        .usernameParameter("userId").passwordParameter("password")
        .defaultSuccessUrl("/user/list", true))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/login").permitAll() // 直リンクOK
            .requestMatchers("/user/signup").permitAll() // 直リンクOK
            .anyRequest().authenticated());
    http.csrf().disable();
    return http.build();
  }

  /**
   * 認証の設定
   * 
   * @return
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    // インメモリ認証
    PasswordEncoder encoder = passwordEncoder();
    UserDetails user = // userを追加
        User.withUsername("user").password(encoder.encode("user")).roles("GENERAL").build();
    UserDetails admin = // adimnを追加
        User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build();
    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
