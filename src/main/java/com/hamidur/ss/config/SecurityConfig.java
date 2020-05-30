package com.hamidur.ss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(final DataSource dataSource, final PasswordEncoder passwordEncoder)
    {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        /*
            - Multiple users defined in-memory.
            - With H2, it comes with default schema and users from below UserDetails. It can be configured as well too
                - with default -> withDefaultSchema() must be invoked to use default tables, users
            - Spring Boot > 2.0 requires password to be encoded.
            - If a single user defined in properties, it will be overridden and will no longer work.
            - User(s) defined in configuration must be assigned to some roles, except in properties file. If ignored
                roles in properties, default "USER" will be applied.
         */

        auth
                .jdbcAuthentication()
                .dataSource(this.dataSource)
                .passwordEncoder(this.passwordEncoder)
                .withDefaultSchema()
                    .withUser("username1")
                    .password(this.passwordEncoder.encode("userpass1"))
                    .roles("USER")
                .and()
                    .withUser("username2")
                    .password(this.passwordEncoder.encode("userpass2"))
                    .roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /*
            -H2
                - When using H2 with security we must allow the console (if used) to user (with appropriate roles)
                - We must disable CSRF. csrf().disable()
                - We must disable frameOptions from headers or restrict it to same origin since H2 runs inside a frame
         */

        http
                .authorizeRequests()
                    .antMatchers("/api/admin").hasRole("ADMIN")
                    .antMatchers("/h2-console/**").hasRole("ADMIN")
                    .antMatchers("/api/user").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/user/{id}").hasRole("USER")
                    .antMatchers("/api/public/**").permitAll()
                .and()
                    .formLogin()
                    .permitAll()
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()
                .and()
                    .csrf()
                    .disable()
                .httpBasic();
    }
}
