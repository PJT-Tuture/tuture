package com.tuture.global.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.tuture.model.dao")
@Configuration
public class MybatisConfig {
}
