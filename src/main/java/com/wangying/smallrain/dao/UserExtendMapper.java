package com.wangying.smallrain.dao;

import com.wangying.smallrain.dao.UserMapper;
import com.wangying.smallrain.entity.User;

public interface UserExtendMapper extends UserMapper {

  /**
   * 根据用户名获取用户
   * @param username
   * @return
   */
  User selectByUserName(String userName);
  
}
