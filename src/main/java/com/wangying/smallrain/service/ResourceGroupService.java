package com.wangying.smallrain.service;

import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.ResourceGroup;
import com.wangying.smallrain.entity.query.BaseQueryEntity;


public interface ResourceGroupService {

  public PageBean<ResourceGroup> getResourceGroupList(BaseQueryEntity query);
  
  int deleteResourceGroup(String resGroupId);
  
  int addOrupdateResourceGroup(ResourceGroup resourceGroup);
  
}
