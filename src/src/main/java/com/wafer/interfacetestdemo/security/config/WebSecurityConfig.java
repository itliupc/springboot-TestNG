package com.wafer.interfacetestdemo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
  @Autowired
  private JwtAuthenticationEntryPoint unauthorizedHandler;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
      authenticationManagerBuilder
              .userDetailsService(this.userDetailsService)
              .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
      return new JwtAuthenticationTokenFilter();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
              // 由于使用的是JWT，我们这里不需要csrf（跨站请求伪造）
              .csrf().disable()
              // 认证不通过的处理
              .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

              // 基于token，所以不需要session
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

              .authorizeRequests()
              //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

              // 允许对于网站静态资源的无授权访问
              .antMatchers(
                      HttpMethod.GET,
                      "/**/swagger-resources/**",
                      "/**/api-docs/**",
                      "/**/webjars/**",
                      "/**/static/**",
                      "/*.html",
                      "/favicon.ico",
                      "/**/*.html",
                      "/**/*.css",
                      "/**/*.js"
              ).permitAll()
              // websocket 链接
              .antMatchers("**/socket/**").permitAll()
              // 对于获取token的rest api要允许匿名访问
              .antMatchers("/api/v1/login").permitAll()
              .antMatchers("/api/v1/dataexport/**").permitAll()
              .antMatchers("/api/v1/dss/**").permitAll()
              // .access("hasRole('ADMIN') and hasRole('DBA')")
              .antMatchers(HttpMethod.POST, "/api/v1/user").permitAll() // 注册用户
              .antMatchers("/api/v1/logout").permitAll()
              // 除上面外的所有请求全部需要鉴权认证
              .anyRequest().authenticated();
              //.and().logout().logoutUrl("/api/v1/logout").logoutSuccessUrl("/api/v1/login").permitAll()
      
      // 添加JWT filter
      httpSecurity
              .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
      
      // role
//      httpSecurity.authorizeRequests().antMatchers("").hasRole("");

      // 禁用缓存
      httpSecurity.headers().cacheControl();
  }
}
