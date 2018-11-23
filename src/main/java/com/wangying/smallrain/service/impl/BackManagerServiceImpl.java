package com.wangying.smallrain.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangying.smallrain.dao.LocalConfigDataDao;
import com.wangying.smallrain.service.BackManagerService;

@Service
public class BackManagerServiceImpl implements BackManagerService {

  @Autowired
  LocalConfigDataDao localConfigDataDao;
  
  @Override
  public Map<String, Object> managerBackMenu(String topMenuId) {
    // TODO Auto-generated method stub
    return localConfigDataDao.loadManagerBackMenuData(topMenuId);
  }

}
