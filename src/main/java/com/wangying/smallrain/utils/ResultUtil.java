package com.wangying.smallrain.utils;

import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.enums.ResultCode;

/**
 * @author wangying.dz3 响应结果生成工具
 */
public class ResultUtil {
  private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

  public static Result success(Object data) {
    return new Result().setStatus(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
  }

  public static Result fail(ResultCode code, String message) {
    return new Result().setStatus(code).setMessage(message);
  }

  public static Result exception(String message) {
    // TODO Auto-generated method stub
    return new Result().setStatus(ResultCode.EXCEPTION).setMessage(message);
  }

}
