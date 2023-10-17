package com.example.springapp.security;

import com.example.springapp.filter.CustomAuthenticationFilter;
import com.example.springapp.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
    customAuthenticationFilter.setFilterProcessesUrl("/api/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests()
      .antMatchers(GET, "/autos/**", "/personnels/**", "/routes/**", "/journals/**")
      .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
    http.authorizeRequests()
      .antMatchers(POST, "/autos/**", "/personnels/**", "/routes/**", "/journals/**")
      .hasAuthority("ROLE_ADMIN");
    http.authorizeRequests()
      .antMatchers(PUT, "/autos/**", "/personnels/**", "/routes/**", "/journals/**")
      .hasAuthority("ROLE_ADMIN");
    http.authorizeRequests()
      .antMatchers(DELETE, "/autos/**", "/personnels/**", "/routes/**", "/journals/**")
      .hasAuthority("ROLE_ADMIN");

    http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAuthority("ROLE_ADMIN");
    http.authorizeRequests().antMatchers(POST, "/api/user/log").hasAuthority("ROLE_ADMIN");
    http.authorizeRequests().antMatchers("/api/login/**").permitAll();
    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
