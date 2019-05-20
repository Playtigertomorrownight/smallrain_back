package com.wangying.smallrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangying.smallrain.dao.BaseConfigsMapper;
import com.wangying.smallrain.entity.BaseConfigs;
import com.wangying.smallrain.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

  @Autowired
  BaseConfigsMapper baseConfigsMapper;
  
  @Override
  public boolean addConfig(BaseConfigs config) {
    // TODO Auto-generated method stub
    return baseConfigsMapper.insert(config)!=0;
  }

  @Override
  public boolean updateConfig(BaseConfigs config) {
    // TODO Auto-generated method stub
    return baseConfigsMapper.updateByPrimaryKey(config)!=0;
  }

  @Override
  public boolean deleteOneConfig(String key) {
    // TODO Auto-generated method stub
    return baseConfigsMapper.deleteByPrimaryKey(key)!=0;
  }

  @Override
  public BaseConfigs selectOneConfig(String key) {
    // TODO Auto-generated method stub
    return baseConfigsMapper.selectByPrimaryKey(key);
  }

  @Override
  public List<BaseConfigs> selectAllConfig() {
    // TODO Auto-generated method stub
    return baseConfigsMapper.selectAllConfig();
  }

}
