package com.wangying.smallrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.wangying.smallrain.configs.BaseConfig;
import com.wangying.smallrain.dao.ResourceGroupMapper;
import com.wangying.smallrain.dao.extend.ResourceExtendMapper;
import com.wangying.smallrain.entity.PageBean;
import com.wangying.smallrain.entity.Resource;
import com.wangying.smallrain.entity.query.ResourceQueryEntity;
import com.wangying.smallrain.service.ResourceService;
import com.wangying.smallrain.utils.BaseUtils;
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private ResourceExtendMapper resourceMapper;

  @Autowired
  private ResourceGroupMapper resourceGroupMapper;
  
  @Autowired
  private BaseConfig baseConfig;
  
  @Override
  public boolean add(Resource res) {
    if(BaseUtils.isEmpty(res.getGroupId())) {   //设置组Id
      res.setGroupId(baseConfig.getDefaultResourceGroup());
    }
    if(BaseUtils.isEmpty(res.getId())) {
      res.setId(BaseUtils.createUUID());  //设置ID
      if(resourceMapper.insert(res)!=0) {
        resourceGroupMapper.addResourceCount(1, res.getGroupId());
        return true;
      }
      return false;
    }else {
      return update(res);
    }
    
  }

  @Override
  public boolean update(Resource res) {
    Resource targetRes = resourceMapper.selectByPrimaryKey(res.getId());
    BaseUtils.copyNotNullProperties(res, targetRes,"id");  //将出了ID的非空属性复制到原对象
    return resourceMapper.updateByPrimaryKey(targetRes)!=0;
  }
  
  
  @Override
  public PageBean<Resource> getResourceList(ResourceQueryEntity query) {
    // TODO Auto-generated method stub
    query = null==query?new ResourceQueryEntity():query;
    if(query.getPageSize()>0) {
      PageHelper.startPage(query.getPageNum(), query.getPageSize());  //mapper方法调用之前进行分页设置
    }
    List<Resource> resouceList = resourceMapper.selectRecordsByQuery(query);
    int countNum = resourceMapper.getCountByQuery(query);
    countNum = countNum==0?1:countNum;
    PageBean<Resource> pageData = new PageBean<>(query.getPageNum(), query.getPageSize()==0?countNum:query.getPageSize(),countNum);
    pageData.setItems(resouceList);
    
    return pageData;
  }

  @Override
  public Resource getResourceById(String resId) {
    // TODO Auto-generated method stub
    return resourceMapper.selectByPrimaryKey(resId);
  }

  @Override
  public boolean delete(String resId) {
    // TODO Auto-generated method stub
    Resource res = resourceMapper.selectByPrimaryKey(resId);
    if(null == res) return true;
    if(resourceMapper.deleteByPrimaryKey(res.getGroupId())!=0) {
      resourceGroupMapper.addResourceCount(-1, res.getGroupId());
      return true;
    }
    return false;
  }

}
