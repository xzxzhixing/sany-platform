package com.sany.platform.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.sany.platform.mapper")
public class MyBatisConfig {

}
