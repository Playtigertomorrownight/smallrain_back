package com.wangying.smallrain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wangying.smallrain.entity.BaseConfigs;

@Service
public interface ConfigService {

   /**
    * 添加一条配置
   * @param config
   * @return
   */
  public boolean addConfig(BaseConfigs config);
  
  /**
   * 更新一条配置
  * @param config
  * @return
  */
  public boolean updateConfig(BaseConfigs config);
  
  /**
   * 查询一条配置
  * @param config
  * @return
  */
  public boolean deleteOneConfig(String key);
  
  /**
   * 查询一条配置
  * @param config
  * @return
  */
  public BaseConfigs selectOneConfig(String key);
  
  /**
   * 查询所有配置
  * @param config
  * @return
  */
  public List<BaseConfigs> selectAllConfig();
 
 
}
