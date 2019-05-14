package com.wangying.smallrain.entity;

/**
 * 用于持久化一些可更改配置
 * @author wangying.dz3
 *
 */
public class BaseConfigs {

  private String key;
  private String value;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key == null ? null : key.trim();
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value == null ? null : value.trim();
  }
}