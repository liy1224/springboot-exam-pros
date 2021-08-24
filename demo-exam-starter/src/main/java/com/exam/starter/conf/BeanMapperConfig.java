package com.exam.starter.conf;

import com.exam.starter.compoment.BeanMapper;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanMapperConfig {
    @Autowired
    private Mapper mapper;

    public BeanMapperConfig() {
    }

    @Bean
    public BeanMapper beanMapper(Mapper mapper) {
        return new BeanMapper(mapper);
    }
}