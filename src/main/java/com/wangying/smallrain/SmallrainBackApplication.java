package com.wangying.smallrain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.wangying.smallrain.dao")
@EnableTransactionManagement
public class SmallrainBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallrainBackApplication.class, args);
	}
	
}
