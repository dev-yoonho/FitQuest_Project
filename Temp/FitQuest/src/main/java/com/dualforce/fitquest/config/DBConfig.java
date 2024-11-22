package com.dualforce.fitquest.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.dualforce.fitquest.model.dao")
public class DBConfig {
}
