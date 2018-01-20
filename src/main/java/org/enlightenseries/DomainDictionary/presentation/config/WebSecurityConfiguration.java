package org.enlightenseries.DomainDictionary.presentation.config;

import org.enlightenseries.DomainDictionary.presentation.config.security.AuthenticationFailureHandler;
import org.enlightenseries.DomainDictionary.presentation.config.security.AuthenticationLogoutSuccessHandler;
import org.enlightenseries.DomainDictionary.presentation.config.security.AuthenticationSuccessHandler;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final UserDetailsService userDetailsService;

  public WebSecurityConfiguration(
    AuthenticationManagerBuilder _authenticationManagerBuilder,
    UserDetailsService _userDetailsService
  ) {
    this.authenticationManagerBuilder = _authenticationManagerBuilder;
    this.userDetailsService = _userDetailsService;
  }

  @PostConstruct
  public void init() {
    try {
      authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
    } catch (Exception e) {
      throw new BeanInitializationException("Security configuration failed", e);
    }
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new AuthenticationSuccessHandler();
  }

  @Bean
  public AuthenticationFailureHandler authenticationFailureHandler() {
    return new AuthenticationFailureHandler();
  }

  @Bean
  public AuthenticationLogoutSuccessHandler authenticationLogoutSuccessHandler() {
    return new AuthenticationLogoutSuccessHandler();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
        .antMatchers("/**.{js,html}")
        .antMatchers("/h2-console/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin()
      .loginProcessingUrl("/api/login")
      .successHandler(authenticationSuccessHandler())
      .failureHandler(authenticationFailureHandler())
      .usernameParameter("username")
      .passwordParameter("password")
      .permitAll();

    http.logout()
      .logoutUrl("/api/logout")
      .logoutSuccessHandler(authenticationLogoutSuccessHandler())
      .permitAll();

    http.authorizeRequests()
      .antMatchers(HttpMethod.GET).permitAll()
      .antMatchers("/api/**").authenticated();
  }
}
