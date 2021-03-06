package org.enlightenseries.DomainDictionary.presentation.config;

import org.enlightenseries.DomainDictionary.presentation.config.security.AuthenticationFailureHandler;
import org.enlightenseries.DomainDictionary.presentation.config.security.AuthenticationLogoutSuccessHandler;
import org.enlightenseries.DomainDictionary.presentation.config.security.AuthenticationSuccessHandler;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
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
    http.csrf()
      .disable();
      //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    http.exceptionHandling()
      .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
      .accessDeniedHandler(new AccessDeniedHandlerImpl());

    http.formLogin()
      .loginProcessingUrl("/api/login")
      .usernameParameter("username")
      .passwordParameter("password")
      .successHandler(authenticationSuccessHandler())
      .failureHandler(authenticationFailureHandler())
      .permitAll();

    http.logout()
      .logoutUrl("/api/logout")
      .logoutSuccessHandler(authenticationLogoutSuccessHandler())
      .permitAll();

    http.authorizeRequests()
      .antMatchers(HttpMethod.GET).permitAll()
      .antMatchers("/api/user").permitAll()
      .antMatchers("/api/**").authenticated();
  }
}
