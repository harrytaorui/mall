package com.harry.mall.config;

import com.harry.mall.common.utils.JwtTokenUtil;
import com.harry.mall.component.JwtAuthenticationTokenFilter;
import com.harry.mall.component.RestAuthenticationEntryPoint;
import com.harry.mall.component.RestfulAccessDeniedHandler;
import com.harry.mall.dto.AdminUserDetails;
import com.harry.mall.mbg.model.UmsAdmin;
import com.harry.mall.mbg.model.UmsPermission;
import com.harry.mall.service.UmsAdminService;
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
  private final UmsAdminService adminService;
  private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
  private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  private final JwtTokenUtil jwtTokenUtil;

  public SecurityConfig(
      UmsAdminService adminService,
      RestfulAccessDeniedHandler restfulAccessDeniedHandler,
      RestAuthenticationEntryPoint restAuthenticationEntryPoint,
      UserDetailsService userDetailsService,
      JwtTokenUtil jwtTokenUtil) {
    this.adminService = adminService;
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
            "/v3/api-docs/**")
        .permitAll()
        .antMatchers("/admin/login", "/admin/register")
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS)
        .permitAll()
        .antMatchers("/**")
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
      UmsAdmin admin = adminService.getAdminByUserName(username);
      if (admin != null) {
        List<UmsPermission> permissionList = adminService.getPermissionList(admin.getId());
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
