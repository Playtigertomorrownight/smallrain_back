package com.wangying.smallrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.wangying.smallrain.dao.extend.ResourceExtendMapper;
import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.ResourceService;
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceExtendMapper resourceMapper;
  
  @Override
  public PageBean<Resource> getResourceList(ResourceQueryEntity query) {
    // TODO Auto-generated method stub
    query = null==query?new ResourceQueryEntity():query;
    PageHelper.startPage(query.getPageNum(), query.getPageSize());  //mapper方法调用之前进行分页设置
    List<Resource> resouceList = resourceMapper.selectRecordsByQuery(query);
    int countNum = resourceMapper.getCountByQuery(query);
    PageBean<Resource> pageData = new PageBean<>(query.getPageNum(), query.getPageSize(),countNum);
    pageData.setItems(resouceList);
    
    return pageData;
  }

}
