package com.hamidur.ss.config;

import com.hamidur.ss.auth.services.AppUserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        /*
            BCrypt Version (All versions generates the same password), strength in power of 2,
            SecureRandom to randomize the generated hash
        */
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2B, 10, new SecureRandom());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AppUserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
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

        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /*
            - httpBasic() provides very basic http authentication with username and password
            - formLogin() provides more fine grain control on authentication, should be used over httpBasic() on webapp
            - H2
                - When using H2 with security we must allow the console (if used) to user (with appropriate roles)
                - We must disable CSRF. csrf().disable()
                - We must disable frameOptions from headers or restrict it to same origin since H2 runs inside a frame
            - Method level security
                - @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) in security config at class level
                - add @Secured on the desired method
                - @Secured can be used only with 1 role. or can be used for multiple but another @ can be used easily
                - @PreAuthorize takes multiple roles with AND keyword in expression
         */

        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.DELETE, "/api/v1/r/delete/comment/{\\d+}").hasAnyRole("ADMIN", "USER")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/r/delete/article/{\\d+}").hasAnyRole("ADMIN", "PUBLISHER")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/r/delete/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/api/v1/r/insert/article").hasAnyRole("ADMIN", "PUBLISHER")
                    .antMatchers(HttpMethod.POST, "/api/v1/r/insert/comment/article").hasAnyRole("ADMIN", "USER")
                    .antMatchers(HttpMethod.POST, "/api/v1/r/insert/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/v1/r/update/comment").hasAnyRole("ADMIN", "USER")
                    .antMatchers(HttpMethod.PUT, "/api/v1/r/update/article").hasAnyRole("ADMIN", "EDITOR")
                    .antMatchers(HttpMethod.PUT, "/api/v1/r/update/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/v1/r/**").hasAnyRole("ADMIN", "USER", "EDITOR", "PUBLISHER")
                    .antMatchers("/api/v1/public/**").permitAll()
                .and()
                    .formLogin()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/api/v1/public/access-denied")
                .and()
                    .headers()
                    .frameOptions()
                    .disable()
                .and()
                    .cors()
                    .disable()
                    .csrf()
                    .disable()
                .httpBasic();
    }
}
