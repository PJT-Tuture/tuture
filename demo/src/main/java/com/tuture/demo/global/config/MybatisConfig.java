package com.tuture.demo.global.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.tuture.demo.model.dao")
@Configuration
public class MybatisConfig {
}
