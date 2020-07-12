package com.hamidur.ss.config;

import com.hamidur.ss.util.ModelConverter;
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

    @Bean
    public ModelConverter modelConverter()
    {
        return new ModelConverter();
    }
}
