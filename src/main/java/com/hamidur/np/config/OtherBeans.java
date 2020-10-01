package com.hamidur.np.config;

import com.hamidur.np.util.ModelConverter;
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
