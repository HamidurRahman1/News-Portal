package com.hamidur.ss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class PasswordEncoderConfig
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
}
