package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

//    @Bean
//    //I introduce my own user, manually user creation;
//    //I pass this parameter, because I'm gonna use method of that PasswordEncoder interface:
//    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//
//        //UserDetails user1 = new User();
//        //create certain spring's users:
//        List<UserDetails> userList = new ArrayList<>();
//
//        userList.add(
//        //I 'm giving username, and encoded password; GrantedAuthority -whenever we define our authorities, we need to define true, one of the impl of grantedAuthority()method.
//                new User("mike", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))) // spring follow this name of convention.
//        );
//
//        userList.add(
//                new User("ozzy", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER")))
//        );
//        // We should provide one of the class Impl of UserServices. There are a bunch of implementations of UserService interface.
//        // Which one we should choose - it depends on where we are going to save that user? in the DB directly or in the memory.
//        return new InMemoryUserDetailsManager(userList);
//
//
//    }

    //For the filtering pages:
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                //in my app, every end user has a role, base on the roles, the end user should only able to see some specific pages:
                .authorizeRequests()
//                .antMatchers("/user/**").hasRole("ADMIN")  // ROLE_ADMIN
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
//                .antMatchers("/task/**").hasAnyRole("EMPLOYEE", "ADMIN")
//                .antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE")

                //filtering of pages and directory, everybody should have acces those pages, no need to creaty security for these pages,
                // I define all excluded pages:
                .antMatchers(
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                //.httpBasic()
                .formLogin()
                    .loginPage("/login")

                    .defaultSuccessUrl("/welcome")

                    .failureUrl("/login?error=true")
                //this login form should be accessible for all end user:
                    .permitAll()
                .and().build();
    }
}
