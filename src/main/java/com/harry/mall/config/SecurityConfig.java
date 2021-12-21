package com.harry.mall.config;

import com.harry.mall.common.utils.JwtTokenUtil;
import com.harry.mall.component.JwtAuthenticationTokenFilter;
import com.harry.mall.component.RestAuthenticationEntryPoint;
import com.harry.mall.component.RestfulAccessDeniedHandler;
import com.harry.mall.dto.AdminUserDetails;
import com.harry.mall.mbg.model.UmsAdmin;
import com.harry.mall.mbg.model.UmsPermission;
import com.harry.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UmsAdminService umsAdminService;
  private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
  private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  private final JwtTokenUtil jwtTokenUtil;

  public SecurityConfig(
      RestfulAccessDeniedHandler restfulAccessDeniedHandler,
      RestAuthenticationEntryPoint restAuthenticationEntryPoint,
      JwtTokenUtil jwtTokenUtil) {
    this.restfulAccessDeniedHandler = restfulAccessDeniedHandler;
    this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()
        .antMatchers(
            HttpMethod.GET,
            "/",
            "/.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**")
        .permitAll()
        .antMatchers("/admin/login", "/admin/register")
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS)
        .permitAll()
        .anyRequest()
        .authenticated();
    httpSecurity.headers().cacheControl();
    httpSecurity.addFilterBefore(
        jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    httpSecurity
        .exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler)
        .authenticationEntryPoint(restAuthenticationEntryPoint);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      UmsAdmin admin = umsAdminService.getAdminByUserName(username);
      if (admin != null) {
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(admin.getId());
        return new AdminUserDetails(admin, permissionList);
      }
      throw new UsernameNotFoundException("Username or password incorrect");
    };
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter(userDetailsService(), jwtTokenUtil);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
