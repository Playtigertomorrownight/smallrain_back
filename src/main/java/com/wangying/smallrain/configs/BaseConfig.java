package com.wangying.smallrain.configs;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

/**
 * @author wangying.dz3
 *
 */
/**
 * @author wangying.dz3
 *
 */
@Configuration
@ConfigurationProperties(prefix = "app.smallrain")
public class BaseConfig {

  //设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
  private long maxFileSize;
  //设置总上传数据总大小
  private long maxRequestSize;
  //服务域名
  private String dommainName;
  //默认资源组ID
  private String defaultResourceGroup;
  
  public long getMaxFileSize() {
    return maxFileSize;
  }

  public long getMaxRequestSize() {
    return maxRequestSize;
  }

  public void setMaxFileSize(long maxFileSize) {
    this.maxFileSize = maxFileSize;
  }

  public void setMaxRequestSize(long maxRequestSize) {
    this.maxRequestSize = maxRequestSize;
  }

  public String getDommainName() {
    return dommainName;
  }

  public void setDommainName(String dommainName) {
    this.dommainName = dommainName;
  }

  public String getDefaultResourceGroup() {
    return defaultResourceGroup;
  }

  public void setDefaultResourceGroup(String defaultResourceGroup) {
    this.defaultResourceGroup = defaultResourceGroup;
  }

  /**
   * 上传文件基本配置
   * @return
   */
  @Bean 
  public MultipartConfigElement multipartConfigElement() { 
      MultipartConfigFactory factory = new MultipartConfigFactory();
      //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
      factory.setMaxFileSize(DataSize.of(maxFileSize, DataUnit.MEGABYTES));
      /// 设置总上传数据总大小
      factory.setMaxRequestSize(DataSize.of(maxRequestSize, DataUnit.MEGABYTES)); 
      //Sets the directory location wherefiles will be stored.
      //factory.setLocation("路径地址");
      return factory.createMultipartConfig(); 
  } 
  
  
}
