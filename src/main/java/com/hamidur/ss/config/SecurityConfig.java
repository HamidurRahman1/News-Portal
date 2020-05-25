package com.hamidur.ss.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${server.servlet.context-path}")
    private String appPath;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        /*
            - Multiple users defined in-memory.
            - Spring Boot > 2.0 requires password to be encoded.
            - If a single user defined in properties, it will be overridden and will no longer work.
            - User(s) defined in configuration must be assigned to some roles, except in properties file. If ignored
                roles in properties, default "USER" will be applied.
         */

        auth
                .inMemoryAuthentication()
                    .withUser("username1")
                    .password("{noop}userpass1")
                    .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                    .antMatchers(appPath, appPath+"/api/public/**")
                    .permitAll()
                    .and()
                .formLogin()
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .and()
                .httpBasic();
    }
}
