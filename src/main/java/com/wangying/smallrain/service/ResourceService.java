package com.wangying.smallrain.service;

import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.Result;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;


public interface ResourceService {

  public PageBean<Resource> getResourceList(ResourceQueryEntity query);
  
  public Result addOrUpdateResource(Resource res);
  
  public Resource getResourceById(String resId);
  
}
