package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(@Lazy SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       // use SecurityFilterChain interface and HttpSecurity as a bean to introduce our own validation from to Spring
        return http
                .authorizeRequests()
                .antMatchers(
                        "/",
                         "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                //login functionality
                    //will use formLogin() option
                    .formLogin()
                    .loginPage("/login")//End point will be "/login"
                     // after login, user land to landing page based on the role (will be handled in "AuthSuccess Handler" class)
                     .successHandler(authSuccessHandler)
                //Logout functionality
                    .failureUrl("/login?error=true") // for unsuccessful logins, the end point wil be "/login?error=true"
                    .permitAll() // should be accessible by anyone
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //End point will be "/logout"
                    .logoutSuccessUrl("/login")
                .and()
                //"Remember Me" functionality
                    .rememberMe()
                    .tokenValiditySeconds(864000) //Token will be valid for 864000 seconds (240 hours)
                    .userDetailsService(securityService)
                .and().build(); // at the end finish everything we put .build()

    //security part added
    }
}
