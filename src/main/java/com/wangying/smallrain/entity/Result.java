package com.wangying.smallrain.entity;

import com.alibaba.fastjson.JSON;
import com.wangying.smallrain.entity.enums.ResultCode;

/**
 * @author wangying 统一API响应结果封装
 * @param <T>
 */
public class Result {
  private int status;
  private String message;
  private Object data;

  public Result setStatus(ResultCode resultCode) {
    this.status = resultCode.code();
    return this;
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public Result setMessage(String message) {
    this.message = message;
    return this;
  }

  public Object getData() {
    return data;
  }

  public Result setData(Object data) {
    this.data = data;
    return this;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
