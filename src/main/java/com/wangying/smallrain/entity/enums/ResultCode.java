package com.wangying.smallrain.entity.enums;

/**
 * @author wangying 响应码枚举
 */
public enum ResultCode {

  // 0为正常，-1为代码异常，1为业务逻辑校验异常

  SUCCESS(0), // 成功
  EXCEPTION(-1), // 代码异常
  ERROR(1), // 为业务逻辑校验异常
  FAIL(-2); // 其他错误

  private final int code;

  ResultCode(int code) {
    this.code = code;
  }

  public int code() {
    return code;
  }
}
