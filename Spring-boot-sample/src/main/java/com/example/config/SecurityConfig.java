package com.example.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private UserDetailsService userDetailsService;

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
        .authorizeHttpRequests(authz -> authz.requestMatchers("/login").permitAll() // 直リンクOK
            .requestMatchers("/user/signup").permitAll() // 直リンクOK
            .anyRequest().authenticated());
    http.csrf().disable();
    return http.build();
  }

  @Bean
  public UserDetailsManager users(DataSource dataSource) {
    String userQuery =
        "select user_id as username,password,true as enabled from m_user where user_id = ?";
    String authoritiesQuery = "select user_id,role from m_user where user_id = ?";

    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
    users.setUsersByUsernameQuery(authoritiesQuery);
    users.setAuthoritiesByUsernameQuery(authoritiesQuery);

    return users;
  }

  // /**
  // * 認証の設定
  // *
  // * @return
  // */
  // @Bean
  // public InMemoryUserDetailsManager userDetailsService() {
  // // インメモリ認証
  // PasswordEncoder encoder = passwordEncoder();
  // UserDetails user = // userを追加
  // User.withUsername("user").password(encoder.encode("user")).roles("GENERAL").build();
  // UserDetails admin = // adimnを追加
  // User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build();
  // return new InMemoryUserDetailsManager(user, admin);
  // }
  //
  // @Bean
  // public PasswordEncoder passwordEncoder() {
  // return new BCryptPasswordEncoder();
  // }
}
