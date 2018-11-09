package com.wangying.smallrain.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WeChatConfig {
  
  private String appId ;
  private String appsecret;
  private String accessTokenUrl;
  private String menusCreateUrl;
  private String menusGetUrl;
  private String menusDeleteUrl;
  
  
  public String getAppId() {
    return appId;
  }
  public String getAppsecret() {
    return appsecret;
  }
  public void setAppId(String appId) {
    this.appId = appId;
  }
  public void setAppsecret(String appsecret) {
    this.appsecret = appsecret;
  }
  public String getAccessTokenUrl() {
    return accessTokenUrl;
  }
  public void setAccessTokenUrl(String accessTokenUrl) {
    this.accessTokenUrl = accessTokenUrl;
  }
  public String getMenusCreateUrl() {
    return menusCreateUrl;
  }
  public void setMenusCreateUrl(String menusCreateUrl) {
    this.menusCreateUrl = menusCreateUrl;
  }
  public String getMenusGetUrl() {
    return menusGetUrl;
  }
  public String getMenusDeleteUrl() {
    return menusDeleteUrl;
  }
  public void setMenusGetUrl(String menusGetUrl) {
    this.menusGetUrl = menusGetUrl;
  }
  public void setMenusDeleteUrl(String menusDeleteUrl) {
    this.menusDeleteUrl = menusDeleteUrl;
  }
  
}
