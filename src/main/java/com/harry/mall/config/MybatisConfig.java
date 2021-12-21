package com.harry.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.harry.mall.mbg.mapper", "com.harry.mall.dao"})
public class MybatisConfig {}
