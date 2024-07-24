package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {// we create this Config class to create bigger Bean implementation, we can write as a Separate Config class

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

//    @Bean
//    public UserDetailsService userDetailService (PasswordEncoder encoder) {
//    List<UserDetails> userList = new ArrayList<>();
//    userList.add(
//            new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        userList.add(
//                new User("ayse", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));
//    return new InMemoryUserDetailsManager(userList);
//    }// FIRSTLY  I CREATE AS A MANUALLY

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       // I use this SecurityFilterChain interface and HttpSecurity as a bean because I want to introduce my own validation from to Spring
        return http
                .authorizeRequests()
               // .antMatchers("/user/**").hasRole("ADMIN") // ROLE_ADMIN--> if I want to hasRole I need to go to my database and I need to change the Role_..
                .antMatchers("/user/**").hasAuthority("Admin") // in my app, certain roles should be able to see certain pages
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
               // .antMatchers("task/**").hasAnyRole("EMPLOYEE", "ADMIN") // I can DEFINE MORE THAN ONE ROLE , DEPENDS BUSINESS LOGIC, now only example we dont have more than roles
               // .antMatchers("task/**").hasAuthority("ROLE_EMPLOYEE")   // hasAuthority needs to be ROLE_,
                .antMatchers( // antMatchers related with the pages
                        "/",
                         "login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll() // everybody should be able to access to these pages, anybody can access
                .anyRequest().authenticated()
                .and()
                //.httpBasic()// I want to use my own login page so I command it
                    .formLogin() // I want to introduce my own validation form to Spring
                    .loginPage("/login")
                    // .defaultSuccessUrl("/welcome")// whenever User is successfull done or User autoticated correct username and password this is the page we gonna land it,
                // it is gonna navigate to welcome page
                // I will modified which pages I will land it after log in the system, i will change default stuff
                    .successHandler(authSuccessHandler)
                    .failureUrl("/login?error=true") // if user put the wrong information username and password, I want to navigate to this URL
                     .permitAll() // should be accessible by anyone
                .and() // we put between this separate part .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login")
                .and()
                    .rememberMe()
                    .tokenValiditySeconds(120)
                    .key("cydeo")
                    .userDetailsService(securityService)
                .and().build(); // at the end finish everything we put .build()
    }
}
