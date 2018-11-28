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
	  //开发时如果有小的改动（不新增方法、变量）时，可以不自动重启而代码生效
	  System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(SmallrainBackApplication.class, args);
	}
	
}
