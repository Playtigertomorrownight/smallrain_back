package com.wangying.smallrain.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.wangying.smallrain.service.WechatService;

/**
 * @author wangying.dz3 程序启动初始化操作
 */
@Component
@Order(1) // 通过order值的大小来决定启动的顺序
public class SmallRainApplicationRunner implements CommandLineRunner {
  
  @Autowired
  private WechatService wechatService;
  
  private Logger log = LoggerFactory.getLogger(SmallRainApplicationRunner.class);

  @Override
  public void run(String... args) throws Exception {
    log.info("程序启动完成后首先需要执行的初始化操作");
    //初始化token 操作
    log.info("-- 1. 获取 token 操作 --");
    String access_token = wechatService.getAccessToken();
    log.info("-- 1. 获取到的 token 是:"+access_token);
  }
}
