package com.wangying.smallrain.service;

import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;


public interface ResourceService {

  public PageBean<Resource> getResourceList(ResourceQueryEntity query);
  
  public boolean add(Resource res);
  
  public boolean update(Resource res);
  
  public boolean delete(String resId);
  
  public Resource getResourceById(String resId);
  
}
