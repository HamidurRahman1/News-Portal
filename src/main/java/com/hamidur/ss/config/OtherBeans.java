package com.hamidur.ss.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtherBeans
{
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
}
