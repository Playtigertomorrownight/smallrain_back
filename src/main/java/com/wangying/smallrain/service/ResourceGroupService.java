package com.wangying.smallrain.service;

import java.util.List;

import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.ResourceGroup;
import com.wangying.smallrain.entity.query.BaseQueryEntity;


public interface ResourceGroupService {

  public PageBean<ResourceGroup> getResourceGroupList(BaseQueryEntity query);
  
  int deleteResourceGroup(String resGroupId);
  
  int addOrupdateResourceGroup(ResourceGroup resourceGroup);
  
  int updateResourceGroupResCount(int addNum,String rgId);
  
  List<ResourceGroup> selectResourceGroupWithIds(List<String> ids);
  
}
